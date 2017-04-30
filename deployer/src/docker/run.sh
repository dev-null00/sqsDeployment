#!/bin/sh
java -Daws.accessKeyId=$AWS_ACCESS_KEY_ID -Daws.secretKey=$AWS_SECRET_ACCESS_KEY -Daws.region=$AWS_REGION -cp deployer-1.0-SNAPSHOT.jar com.theleadengineer.deployment.DeploymentMessageProducer $MESSAGE_TO_SEND
