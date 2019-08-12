package jc.dev.docker.manager;

import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.roots.ModuleRootManager;
import jc.dev.docker.config.SingleFileExecutionConfig;
import jc.dev.docker.configJSON.ConfigContainer;
import jc.dev.docker.configJSON.ConfigJSON;
import com.intellij.openapi.project.Project;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private static final String DOCKER_PS = "docker ps -a --format \"table {{.ID}}|#|{{.Names}}|#|{{.Image}}|#|{{.Status}}\" | (read -r; printf \"%s\\n\" \"$REPLY\"; sort -k 3 )";

    Project project;
    SingleFileExecutionConfig config;

    public Manager(com.intellij.openapi.project.Project project) {
        this.project = project;
        this.config = SingleFileExecutionConfig.getInstance(project);
    }

    public List<Container> dockerPs() {
        List<Container> containers = new ArrayList();
        SSH ssh = new SSH(this.config);
        String dockerPsString = ssh.execCommand(DOCKER_PS);
        String[] dockerPsLines = dockerPsString.split("\\\n");
        for (int i = 1; i < dockerPsLines.length; i++) {
            String[] args = dockerPsLines[i].split("\\|#\\|");
            containers.add(new Container(args[0], args[1], args[2], args[3]));
        }
        return containers;
    }

    public ConfigContainer[] getContainersFromConfigFile() {
        String projectPath = ModuleRootManager.getInstance(ModuleManager.getInstance(this.project).getModules()[0]).getContentRoots()[0].getPath();
        ConfigJSON configJSON = new ConfigJSON(projectPath);
        return configJSON.loadConfig();
    }

    public List<Container> filterContainersWithActions() {
        ConfigContainer[]configContainers = this.getContainersFromConfigFile();
        List<Container> containers = this.dockerPs();
        List<Container> newContainersList = new ArrayList<>();
        for(ConfigContainer configContainer : configContainers) {
            for (Container container: containers) {
                if (configContainer.getName().equals(container.getName())) {
                    newContainersList.add(container);
                    break;
                }
            }
        }
        return newContainersList;
    }
}
