package com.theleadengineer.deployment;

import com.amazonaws.util.StringUtils;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.theleadengineer.deployment.container.AWSContainerInformationModule;
import com.theleadengineer.deployment.messaging.AWSDeploymentModule;
import com.theleadengineer.deployment.messaging.DeploymentMessageService;
import com.theleadengineer.infrastructure.AWSModule;
import com.theleadengineer.deployment.container.ContainerInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Singleton
public class DeploymentMessageConsumer {
    private static final Logger logger = LogManager.getLogger(DeploymentMessageConsumer.class);
    private final DeploymentMessageService deploymentMessageService;
    private final ContainerInfoService containerInfoService;

    @Inject
    public DeploymentMessageConsumer(final DeploymentMessageService deploymentMessageService, final ContainerInfoService containerInfoService) {
        this.deploymentMessageService = deploymentMessageService;
        this.containerInfoService = containerInfoService;
    }

    public static void main(String[] args) throws Exception {
        final Injector injector = Guice.createInjector(new AWSModule(), new AWSDeploymentModule(), new AWSContainerInformationModule());
        final DeploymentMessageConsumer instance = injector.getInstance(DeploymentMessageConsumer.class);
        instance.processNextMessage();
    }

    private void processNextMessage() {
        final String repoName = deploymentMessageService.getNextMessage();
        if(repoName.isEmpty()) {
            logger.info("No message retrieved");
            return;
        }
        final ContainerInfoService.Result latestImageToDeploy = containerInfoService.getLatestImage(repoName);
        logger.info("Image that is to be deployed: {}", latestImageToDeploy);

        deployImage(repoName, latestImageToDeploy);
    }

    private void deployImage(final String repoName, final ContainerInfoService.Result latestImageToDeploy) {
        final Runtime run = Runtime.getRuntime();
        final String cmd = generateDeployOnDockerCommand(repoName, latestImageToDeploy);
        executeShellCommand(run, cmd);
    }

    private String generateDeployOnDockerCommand(final String repoName, final ContainerInfoService.Result latestImageToDeploy) {
        return "docker service create --with-registry-auth --name "+repoName+latestImageToDeploy.getTag() + " -p " + latestImageToDeploy.getTag()+":7122 " + latestImageToDeploy.getRepoURI()+":"+latestImageToDeploy.getTag();
    }

    private void executeShellCommand(final Runtime run, final String cmd) {
        logger.info("Command to run: {}", cmd);
        Process pr;
        try {
            pr = run.exec(cmd);
            pr.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Error: ", e);
        }
    }
}
