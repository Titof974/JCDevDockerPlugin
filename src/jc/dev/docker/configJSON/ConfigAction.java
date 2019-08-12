package jc.dev.docker.configJSON;

import java.util.List;

public class ConfigAction {
    final String name;
    final List<ConfigCommand> commands;

    public ConfigAction(String name, List<ConfigCommand> commands) {
        this.name = name;
        this.commands = commands;
    }

    public String getName() {
        return name;
    }

    public List<ConfigCommand> getCommands() {
        return commands;
    }
}
