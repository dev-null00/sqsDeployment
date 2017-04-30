package com.theleadengineer.deployment.messaging;

public interface DeploymentMessageService {
    void sendMessage(String s);

    String getNextMessage();
}
