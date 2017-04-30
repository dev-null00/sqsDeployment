package com.theleadengineer.infrastructure;

public enum AWSQueues {
    DOCKER_SWARM_1("docker_swarm_deployment_1"),
    ;

    private final String queueName;

    AWSQueues(final String queueName){
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }
}
