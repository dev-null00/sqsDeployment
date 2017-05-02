package com.theleadengineer.deployment.container;

import com.google.inject.AbstractModule;

public class AWSContainerInformationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ContainerInfoService.class).to(ContainerInfoServiceAWS.class);
    }
}
