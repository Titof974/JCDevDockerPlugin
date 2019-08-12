package jc.dev.docker.configJSON;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
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

        this.loadConfig();

    }

    public boolean isValid() {
        return this.file.exists();
    }

    private void loadConfig() {
        if (!this.file.exists()) {
            return;
        }

        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            System.out.println(this.file.getAbsolutePath());
            final Document document= builder.parse(this.file);
            final Element racine = document.getDocumentElement();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
