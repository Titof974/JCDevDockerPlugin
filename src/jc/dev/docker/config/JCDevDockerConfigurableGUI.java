package jc.dev.docker.config;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.project.Project;

import javax.swing.*;

public class JCDevDockerConfigurableGUI {

    private JPanel rootPanel;

    public void createUI(Project project) {
        SingleFileExecutionConfig mConfig = SingleFileExecutionConfig.getInstance(project);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public boolean isModified() {
        boolean modified = false;
        return modified;
    }

    public void apply() {
    }

    public void reset() {
    }
}
