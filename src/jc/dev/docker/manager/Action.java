package jc.dev.docker.manager;

import java.util.*;

public class Action {
    final String name;
    final List<String> commands;
    final boolean local;
    Map<Action.TEMPLATE, String> params;

    public enum TEMPLATE {
        CONTAINER_NAME("%CONTAINER_NAME%"),
        CONTAINER_ID("%CONTAINER_ID%");

        String value;

        TEMPLATE(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public final static Action DOCKER_STOP = Action.distant("DOCKER STOP", new ArrayList<String>(Arrays.asList("/snap/bin/docker stop %CONTAINER_ID%")));

    public Action(String name, List<String> command, boolean local, Map<Action.TEMPLATE, String> params) {
        this.name = name;
        this.commands = command;
        this.local = local;
        this.params = params;
    }

    public Action(String name, List<String> command, boolean local) {
        this.name = name;
        this.commands = command;
        this.local = local;
    }

    public static Action distant(String name, List<String> command) {
        return new Action(name, command, false);
    }

    public static Action local(String name, List<String> command) {
        return new Action(name, command, true);
    }

    public List<String> getCommands() {
        return formatCommands(this.commands);
    }

    private List<String> formatCommands(List<String> commands) {
        List<String> newCommands = new ArrayList<>();
        for (String command : commands) {
            for(Action.TEMPLATE key : this.params.keySet()) {
                newCommands.add(command.replace(key.getValue(), this.params.get(key)));
            }
        }
        return newCommands;
    }

    public Action setParams(Map<Action.TEMPLATE, String> params) {
        return new Action(this.name, this.commands, this.local, params);
    }

    public boolean isLocal() {
        return this.local;
    }

    public String getName() {
        return this.name;
    }
}
