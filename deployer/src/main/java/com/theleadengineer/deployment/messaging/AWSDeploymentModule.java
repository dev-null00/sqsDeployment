package com.theleadengineer.deployment.messaging;

import com.google.inject.AbstractModule;
import com.theleadengineer.deployment.messaging.DeploymentMessageService;
import com.theleadengineer.deployment.messaging.DeploymentMessageServiceAws;

public class AWSDeploymentModule extends AbstractModule {
    protected void configure() {
        bind(DeploymentMessageService.class).to(DeploymentMessageServiceAws.class);
    }
}
