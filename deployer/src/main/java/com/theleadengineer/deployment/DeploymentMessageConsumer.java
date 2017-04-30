package com.theleadengineer.deployment;

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
        final String imageTagToDeploy = containerInfoService.getLatestImage(repoName);
        logger.info("Image that is to be deployed: {}", imageTagToDeploy);
    }
}
