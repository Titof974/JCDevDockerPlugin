package jc.dev.docker.config;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class JCDevDockerConfigurableGUI {

    private JPanel rootPanel;
    private JTextField userField;
    private JTextField serverField;
    private JTextField privateKeyField;
    private JButton selectButton;
    SingleFileExecutionConfig mConfig;

    public void createUI(Project project) {
        this.mConfig = SingleFileExecutionConfig.getInstance(project);
        this.serverField.setText(this.mConfig.getHost());
        this.userField.setText(this.mConfig.getUser());
        this.privateKeyField.setText(this.mConfig.getPrivateKey());
        this.selectButton.addActionListener((actionEvent) -> {
            this.chooseButtonActionPerformed(actionEvent);
        });
    }

    private void chooseButtonActionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            this.privateKeyField.setText(selectedFile.getAbsolutePath());
        }
    }


    public JPanel getRootPanel() {
        return this.rootPanel;
    }

    public boolean isModified() {
        boolean modified = false;
        modified |= !this.serverField.getText().equals(this.mConfig.getHost());
        modified |= !this.userField.getText().equals(this.mConfig.getHost());
        modified |= !this.privateKeyField.getText().equals(this.mConfig.getPrivateKey());
        return modified;
    }

    public void apply() {
        this.mConfig.setHost(this.serverField.getText());
        this.mConfig.setUser(this.userField.getText());
        this.mConfig.setPrivateKey(this.privateKeyField.getText());
    }

    public void reset() {
        this.serverField.setText(this.mConfig.getHost());
        this.userField.setText(this.mConfig.getUser());
        this.privateKeyField.setText(this.mConfig.getPrivateKey());
    }
}
