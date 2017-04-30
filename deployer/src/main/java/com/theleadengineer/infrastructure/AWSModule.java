package com.theleadengineer.infrastructure;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.services.ecr.AmazonECR;
import com.amazonaws.services.ecr.AmazonECRClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.util.StringUtils;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class AWSModule extends AbstractModule {
    protected void configure() {

    }

    @Provides
    AmazonSQS providesSQSClient(final AWSCredentialsProvider awsCredentialsProvider) {
        // set with -Daws.accessKeyId=<key> -Daws.secretKey=<password> -Daws.region=<region>
        final String region = StringUtils.trim(System.getProperty("aws.region"));
        return AmazonSQSClient.builder().withCredentials(awsCredentialsProvider).withRegion(region).build();
    }

    @Provides
    AWSCredentialsProvider providesAWSCredentials(){
        return new SystemPropertiesCredentialsProvider();
    }

    @Provides
    AmazonECR getECRClient(){
        return AmazonECRClientBuilder.defaultClient();
    }
}
