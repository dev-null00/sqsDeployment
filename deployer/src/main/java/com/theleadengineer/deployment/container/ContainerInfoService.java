package com.theleadengineer.deployment.container;

public interface ContainerInfoService {
    Result getLatestImage(String repositoryName);

    class Result {
        private final String repoURI;
        private final String tag;

        Result(final String repoURI, final String tag) {
            this.repoURI = repoURI;
            this.tag = tag;
        }

        public String getRepoURI() {
            return repoURI;
        }

        public String getTag() {
            return tag;
        }
    }
}
