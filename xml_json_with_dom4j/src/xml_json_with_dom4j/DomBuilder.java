package xml_json_with_dom4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author Alexandru Cazacu
 */
public class DomBuilder {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private Document document;
    private Element currentNode;

    // ----------------------------------------------------------------------------------------------------
    public DomBuilder() {
        document = DocumentHelper.createDocument();
        currentNode = null;
    }

    // ----------------------------------------------------------------------------------------------------
    public void addRoot(String name) {
        if (document.getRootElement() != null) {
            document.getRootElement().setName(name);
        }
        else {
            document.addElement(name);
            currentNode = document.getRootElement();
        }
    }

    // ----------------------------------------------------------------------------------------------------
    public void addNode(String name) {
        currentNode.addElement(name);
    }

    // ----------------------------------------------------------------------------------------------------
    public void addAttribute(String name, String value) {
        currentNode.addAttribute(name, value);
    }

    // ----------------------------------------------------------------------------------------------------
    public void addText(String name) {
        currentNode.addText(name);
    }

    public void clear() {
        document.clearContent();
    }

    // ----------------------------------------------------------------------------------------------------
    public void Print() {
        ShowDOM(document.getRootElement(), 1);
    }

    // ----------------------------------------------------------------------------------------------------
    public void moveUp() {
        int pos = currentNode.getParent().elements().indexOf(currentNode);
        currentNode = currentNode.getParent().elements().get(pos - 1);
    }

    // ----------------------------------------------------------------------------------------------------
    public void moveDown() {
        int pos = currentNode.getParent().elements().indexOf(currentNode);
        currentNode = currentNode.getParent().elements().get(pos + 1);
    }
    // ----------------------------------------------------------------------------------------------------

    public void moveToParent() {
        currentNode = currentNode.getParent();
    }

    // ----------------------------------------------------------------------------------------------------
    public void moveToChild() {
        currentNode = currentNode.elements().get(0);
    }

    // ----------------------------------------------------------------------------------------------------
    public void writeXML(String path) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileWriter(new File(path)), format);
        writer.write(document);
        writer.close();
    }

    // ----------------------------------------------------------------------------------------------------
    public void readXML(String path) throws DocumentException {
        SAXReader reader = new SAXReader();
        document = reader.read(path);
    }

    // ----------------------------------------------------------------------------------------------------
    public void XMLtoJSON(String from, String to) throws DocumentException, FileNotFoundException {

        SAXReader reader = new SAXReader();
        Document doc = DocumentHelper.createDocument();
        doc = reader.read(from);

        String xmlText = doc.asXML();
        System.out.println(doc.asXML());

        String result = XML.toJSONObject(xmlText).toString(4);

        System.out.println(result);

        PrintWriter out = new PrintWriter(to);
        out.write(result);
        out.close();
    }

    // ----------------------------------------------------------------------------------------------------
    public void JSONtoXML(String from, String to) throws FileNotFoundException, IOException {
        File file = new File(from);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        String jsonText = new String(data, "UTF-8");

        JSONObject json = new JSONObject(jsonText);
        String xmlText = XML.toString(json);

        System.out.println(jsonText);
        System.out.println(xmlText);

        PrintWriter out = new PrintWriter(to);
        out.write(xmlText);
        out.close();
    }

    // ----------------------------------------------------------------------------------------------------
    private void ShowDOM(Element element, int depth) {

        if (element == null) {
            return;
        }

        String s = "";
        for (int i = 0; i < depth; i++) {
            s += "---";
        }

        if (element.equals(currentNode)) {
            System.out.print(ANSI_YELLOW);
        }

        System.out.print(s + element.getName());
        for (int i = 0; i < element.attributeCount(); i++) {
            System.out.print(" - " + element.attributes().get(i).getName() + ":" + element.attributes().get(i).getValue());
        }

        System.out.print("   " + element.getText());
        System.out.print(ANSI_RESET);
        System.out.println("");

        for (Element e : element.elements()) {
            ShowDOM(e, depth + 1);
        }
    }
}
