FROM java:8-alpine
ADD deployer-1.0-SNAPSHOT.jar .
ADD run.sh .
RUN chmod a+x run.sh
#ENTRYPOINT ["java", "-Daws.accessKeyId=$AWS_ACCESS_KEY_ID", "-Daws.secretKey=$AWS_SECRET_ACCESS_KEY", "-Daws.region=$AWS_REGION" "-cp","deployer-1.0-SNAPSHOT.jar", "com.theleadengineer.deployment.DeploymentMessageProducer", "$MESSAGE_TO_SEND"]
#CMD ["java", "-cp deployer-1.0-SNAPSHOT.jar com.theleadengineer.deployment.DeploymentMessageProducer"]
CMD ["./run.sh"]