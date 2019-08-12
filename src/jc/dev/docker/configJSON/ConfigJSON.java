package jc.dev.docker.configJSON;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigJSON {
    public static final String FILE_NAME = "JCDD.json";
    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    File file;
    String path;

    public ConfigJSON(String path) {

        this.path = path;
        if (!this.path.endsWith("/")) {
            this.path = this.path + "/";
        }
        this.path = this.path + FILE_NAME;

        this.file = new File(this.path);


    }

    public ConfigContainer[] loadConfig() {
        ConfigContainer[] configContainers = new ConfigContainer[0];
        if (!this.file.exists()) {
            return configContainers;
        }
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(this.file));
            configContainers = gson.fromJson(reader, ConfigContainer[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return configContainers;
    }
}
