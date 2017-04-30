FROM java:8-alpine
ADD deployer-1.0-SNAPSHOT.jar .
ADD run.sh .
RUN chmod a+x run.sh
CMD ["./run.sh"]