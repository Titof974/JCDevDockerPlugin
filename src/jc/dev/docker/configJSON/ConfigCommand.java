package jc.dev.docker.configJSON;

public class ConfigCommand {
    final String type;
    final String command;

    public ConfigCommand(String type, String command) {
        this.type = type;
        this.command = command;
    }

    public String getType() {
        return type;
    }

    public String getCommand() {
        return command;
    }
}
