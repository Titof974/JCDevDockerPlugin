package jc.dev.docker.manager;

import jc.dev.docker.config.SingleFileExecutionConfig;

public class Manager {
    SingleFileExecutionConfig config;

    public Manager(SingleFileExecutionConfig config) {
        this.config = config;
        SSH ssh = new SSH(this.config);
    }

}
