package jc.dev.docker.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

/**
 * PersistentStateComponent keeps project config values.
 * Similar notion of 'preference' in Android
 */
@State(
        name="jc.dev.docker.config.SingleFileExecutionConfig",
        storages = {
                @Storage("jc.dev.docker.config.SingleFileExecutionConfig.xml")}
)
public class SingleFileExecutionConfig implements PersistentStateComponent<SingleFileExecutionConfig> {


    static final String PROJECTDIR = "%PROJECTDIR%";
    static final String FILEDIR = "%FILEDIR%";

    public String host = "";
    public String user = "";
    public String privateKey = "";


    SingleFileExecutionConfig() { }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Nullable
    @Override
    public SingleFileExecutionConfig getState() {
        return this;
    }

    @Override
    public void loadState(SingleFileExecutionConfig singleFileExecutionConfig) {
        XmlSerializerUtil.copyBean(singleFileExecutionConfig, this);
    }

    @Nullable
    public static SingleFileExecutionConfig getInstance(Project project) {
        SingleFileExecutionConfig sfec = ServiceManager.getService(project, SingleFileExecutionConfig.class);
        return sfec;
    }
}