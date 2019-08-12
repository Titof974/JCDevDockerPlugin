package jc.dev.docker.manager;

import cucumber.api.java.cs.A;

import java.util.Map;
import java.util.Set;

public class Action {
    final String name;
    final String command;
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

    public final static Action DOCKER_STOP = Action.distant("DOCKER STOP", "/snap/bin/docker stop %CONTAINER_ID%");

    public Action(String name, String command, boolean local,  Map<Action.TEMPLATE, String> params) {
        this.name = name;
        this.command = command;
        this.local = local;
        this.params = params;
    }

    public Action(String name, String command, boolean local) {
        this.name = name;
        this.command = command;
        this.local = local;
    }

    public static Action distant(String name, String command) {
        return new Action(name, command, false);
    }

    public static Action local(String name, String command) {
        return new Action(name, command, true);
    }

    public String getCommand() {
        String newCommand = this.command;
        for(Action.TEMPLATE key : this.params.keySet()) {
            newCommand = newCommand.replace(key.getValue(), this.params.get(key));
        }

        return newCommand;
    }

    public Action setParams(Map<Action.TEMPLATE, String> params) {
        return new Action(this.name, this.command, this.local, params);
    }

    public boolean isLocal() {
        return this.local;
    }

    public String getName() {
        return this.name;
    }
}
