package com.theleadengineer.deployment;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.theleadengineer.deployment.messaging.AWSDeploymentModule;
import com.theleadengineer.deployment.messaging.DeploymentMessageService;
import com.theleadengineer.infrastructure.AWSModule;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DeploymentMessageConsumer {
    private final DeploymentMessageService deploymentMessageService;

    @Inject
    public DeploymentMessageConsumer(final DeploymentMessageService deploymentMessageService) {
        this.deploymentMessageService = deploymentMessageService;
    }

    public static void main(String[] args) throws Exception {
        final Injector injector = Guice.createInjector(new AWSModule(), new AWSDeploymentModule());
        final DeploymentMessageConsumer instance = injector.getInstance(DeploymentMessageConsumer.class);
        instance.processNextMessage();
    }

    private void processNextMessage() {
        final String message = deploymentMessageService.getNextMessage();
    }
}
