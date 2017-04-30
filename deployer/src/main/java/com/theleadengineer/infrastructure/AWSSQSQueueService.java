package com.theleadengineer.infrastructure;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;

import javax.inject.Inject;

public class AWSSQSQueueService {
    private final AmazonSQS sqsClient;

    @Inject
    public AWSSQSQueueService(final AmazonSQS sqsClient) {
        this.sqsClient = sqsClient;
    }

    public String getUrlForQueue(final AWSQueues awsQueues){
        final GetQueueUrlResult queueUrl = sqsClient.getQueueUrl(awsQueues.getQueueName());
        return queueUrl.getQueueUrl();
    }

}
