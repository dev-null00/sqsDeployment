package com.theleadengineer.deployment;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.theleadengineer.deployment.messaging.AWSDeploymentModule;
import com.theleadengineer.deployment.messaging.DeploymentMessageService;
import com.theleadengineer.infrastructure.AWSModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DeploymentMessageProducer {
    private static Logger logger = LogManager.getLogger(DeploymentMessageProducer.class);
    private final DeploymentMessageService deploymentMessageService;

    @Inject
    public DeploymentMessageProducer(final DeploymentMessageService deploymentMessageService) {
        this.deploymentMessageService = deploymentMessageService;
    }


    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            logger.error("Can only process 1 argument to send a message");
            return;
        }
        logger.info("Starting up");
        final Injector injector = Guice.createInjector(new AWSModule(), new AWSDeploymentModule());
        final DeploymentMessageProducer instance = injector.getInstance(DeploymentMessageProducer.class);
        instance.sendMessage(args[0]);
    }

    private void sendMessage(final String message) {
        deploymentMessageService.sendMessage(message);
    }
}
