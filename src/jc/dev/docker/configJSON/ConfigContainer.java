package jc.dev.docker.configJSON;

import java.util.List;

public class ConfigContainer {
    final String name;
    final List<ConfigAction> actions;

    public ConfigContainer(String name, List<ConfigAction> actions) {
        this.name = name;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public List<ConfigAction> getActions() {
        return actions;
    }
}
