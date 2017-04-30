package com.theleadengineer.deployment.messaging;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.theleadengineer.infrastructure.AWSQueues;
import com.theleadengineer.infrastructure.AWSSQSQueueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

class DeploymentMessageServiceAws implements DeploymentMessageService {
    private static final Logger logger = LogManager.getLogger(DeploymentMessageServiceAws.class);
    private final AmazonSQS sqsClient;
    private final String sqsQueueUrl;

    @Inject
    DeploymentMessageServiceAws(final AmazonSQS sqsClient, final AWSSQSQueueService awsSqsQueueService) {
        this.sqsClient = sqsClient;
        this.sqsQueueUrl = awsSqsQueueService.getUrlForQueue(AWSQueues.DOCKER_SWARM_1);
    }

    public void sendMessage(final String messageString) {
        logger.info("Sending message {} to queue: {}", messageString, sqsQueueUrl);
        sqsClient.sendMessage(new SendMessageRequest(sqsQueueUrl, messageString));
    }

    public String getNextMessage() {
        final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(sqsQueueUrl).withMaxNumberOfMessages(1);
        final List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
        if (messages.isEmpty()) return "";
        final Message message = messages.get(0);
        String messageReceiptHandle = messages.get(0).getReceiptHandle();
        sqsClient.deleteMessage(new DeleteMessageRequest(sqsQueueUrl, messageReceiptHandle));
        return message.getBody();
    }
}
