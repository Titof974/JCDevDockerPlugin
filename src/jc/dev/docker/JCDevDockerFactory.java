package jc.dev.docker;

import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;
import jc.dev.docker.config.SingleFileExecutionConfig;
import jc.dev.docker.configJSON.ConfigJSON;
import jc.dev.docker.manager.Manager;
import jc.dev.docker.manager.SSH;

public class JCDevDockerFactory implements ToolWindowFactory {
    // Create the tool window content.
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
         String projectPath = ModuleRootManager.getInstance(ModuleManager.getInstance(project).getModules()[0]).getContentRoots()[0].getPath();
        System.out.println(ModuleRootManager.getInstance(ModuleManager.getInstance(project).getModules()[0]).getContentRoots()[0].getPath());

        Manager manager = new Manager(project);
        manager.dockerPs();
        //ConfigJSON configJSON = new ConfigJSON(projectPath);

        JCDevDocker window = new JCDevDocker(toolWindow, new SSH(SingleFileExecutionConfig.getInstance(project)), new Manager(project));
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(window.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
