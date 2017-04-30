package com.theleadengineer.deployment.container;

import com.amazonaws.services.ecr.AmazonECR;
import com.amazonaws.services.ecr.model.DescribeRepositoriesRequest;
import com.amazonaws.services.ecr.model.DescribeRepositoriesResult;
import com.amazonaws.services.ecr.model.ListImagesRequest;
import com.amazonaws.services.ecr.model.ListImagesResult;
import com.amazonaws.services.ecr.model.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class ContainerInfoServiceAWS implements ContainerInfoService {
    private static final Logger logger = LogManager.getLogger(ContainerInfoServiceAWS.class);

    private final AmazonECR amazonECR;

    @Inject
    public ContainerInfoServiceAWS(final AmazonECR amazonECR) {
        this.amazonECR = amazonECR;
    }

    @Override
    public String getLatestImage(final String repositoryName) {
        final Repository repoInformation = getRepoInformation(repositoryName);
        final ListImagesResult listImagesResult = amazonECR.listImages(new ListImagesRequest().withRepositoryName(repositoryName).withRegistryId(repoInformation.getRegistryId()));
        return repoInformation.getRepositoryUri()+":"+listImagesResult.getImageIds().get(0).getImageTag();
    }

    private Repository getRepoInformation(final String repositoryName) {
        final DescribeRepositoriesRequest describeRepositoriesRequest = new DescribeRepositoriesRequest().withRepositoryNames(Arrays.asList(repositoryName));
        final DescribeRepositoriesResult describeRepositoriesResult = amazonECR.describeRepositories(describeRepositoriesRequest);
        return describeRepositoriesResult.getRepositories().get(0);
    }
}
