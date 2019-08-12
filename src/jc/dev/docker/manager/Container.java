package jc.dev.docker.manager;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

import static jc.dev.docker.manager.Action.*;

public class Container {
    final String id;
    final String name;
    final String image;

    public Container(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Map<TEMPLATE, String> toMap() {
        Map<TEMPLATE, String> map = new HashedMap();
        map.put(TEMPLATE.CONTAINER_ID, this.id);
        map.put(TEMPLATE.CONTAINER_NAME, this.name);
        return map;
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
}
