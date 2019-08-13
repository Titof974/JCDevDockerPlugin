package jc.dev.docker.manager;

import jc.dev.docker.configJSON.ConfigAction;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static jc.dev.docker.manager.Action.*;

public class Container {
    final String id;
    final String name;
    final String image;
    final Boolean status;
    final List<Action> actions;

    public Container(String id, String name, String image, String rawStatus) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.status = this.checkStatus(rawStatus);
        this.actions = new ArrayList<>();
    }

    public boolean checkStatus(String rawStatus) {
        if (rawStatus.startsWith("Up")) {
            return true;
        }
        return false;
    }

    public Map<TEMPLATE, String> toMap() {
        Map<TEMPLATE, String> map = new HashedMap();
        map.put(TEMPLATE.CONTAINER_ID, this.id);
        map.put(TEMPLATE.CONTAINER_NAME, this.name);
        return map;
    }

    public List<Action> getActions() {
        return this.actions;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.image;
    }

    public boolean getStatus() { return this.status; }
}
