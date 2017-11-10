/* Copyright (C) 2017 Alexandru Cazacu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package xml_json_with_dom4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.json.XML;

/**
 *
 * @author Alexandru Cazacu
 */
public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static Element currentNode = null;

    // ----------------------------------------------------------------------------------------------------
    public static void main(String[] args) throws IOException {

        Document document = DocumentHelper.createDocument();
//        Element root = document.addElement("places");
//        Element place = root.addElement("place").addAttribute("placeId", "place01");
//        place.addElement("city").addText("Brescia");
//
//        OutputFormat format = OutputFormat.createPrettyPrint();
//        XMLWriter writer = new XMLWriter(new FileWriter(new File("example_dom4j_new.xml")), format);
//        writer.write(document);
//        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String command = reader.readLine();
            String arguments[] = command.split("\\s+");

            switch (arguments[0]) {
                case "help":
                    System.out.println("add [-n] [-a] [-t] <param1> <param2> <param3>");
                    System.out.println("cn [-u] / [-d] / [-c] / [-p]");
                    System.out.println("read [--xml] / [--json] <param1>");
                    System.out.println("write [--xml] / [--json] <param1>");
                    System.out.println("print");
                    System.out.println("clear");
                    System.out.println("exit");
                    if (arguments.length >= 2) {
                        // TODO insert comand specific help
                    }
                    break;
                case "add":
                    if (arguments.length >= 3) {
                        // add -r <rootname>
                        if (arguments[1].equals("-r")) {
                            if (document.getRootElement() != null) {
                                document.getRootElement().setName(arguments[2]);
                            }
                            else {
                                document.addElement(arguments[2]);
                                currentNode = document.getRootElement();
                            }
                        }
                        // add -e <nodename>
                        if (arguments[1].equals("-e")) {
                            currentNode.addElement(arguments[2]);
                        }
                        // add -t <text>
                        if (arguments[1].equals("-t")) {
                            currentNode.addText(arguments[2]);
                        }

                        if (arguments.length >= 4) {
                            // add -a <attributeID> <attributeValue>
                            if (arguments[1].equals("-a")) {
                                currentNode.addAttribute(arguments[2], arguments[3]);
                            }

                            if (arguments.length >= 5) {
                                // add -e <nodename> <attributeID> <attributeValue>
                                if (arguments[1].equals("-e")) {
                                    currentNode.addElement(arguments[2]).addAttribute(arguments[3], arguments[4]);
                                }
                            }
                        }
                    }
                    else {
                        System.out.println("Missing argument. See 'help add'.");
                    }
                    break;
                case "cn": // TODO manage errors
                    if (arguments.length >= 2) {
                        // cn -d
                        if (arguments[1].equals("-d")) {
                            int pos = currentNode.getParent().elements().indexOf(currentNode);
                            currentNode = currentNode.getParent().elements().get(pos + 1);
                        }

                        // cn -u
                        if (arguments[1].equals("-u")) {
                            int pos = currentNode.getParent().elements().indexOf(currentNode);
                            currentNode = currentNode.getParent().elements().get(pos - 1);
                        }

                        // cn -c
                        if (arguments[1].equals("-c")) {
                            currentNode = currentNode.elements().get(0);
                        }

                        // cn -p
                        if (arguments[1].equals("-p")) {
                            currentNode = currentNode.getParent();
                        }
                    }
                    else {
                        System.out.println(currentNode.getName());
                    }
                    break;
                case "write": // write [--xml] / [--json] <param1>
                    // If there are 2 arguments.
                    if (arguments.length >= 2) {
                        if (arguments[1].equals("--xml")) {
                            writeXML(document, arguments[2]);
                            System.out.println("Saved at '" + arguments[2] + "'");
                        }
                        if (arguments[1].equals("--json")) {

                        }
                    }
                    else {
                        System.out.println("Missing argument. See 'help save'.");
                    }
                    break;
                case "print":
                    ShowDOM(document.getRootElement(), 1);
                    break;
                case "clear":
                    document.clearContent();
                    break;
                case "exit":
                    System.exit(0);
                default:
                    System.out.println("'" + arguments[0] + "' is not a command. See 'help'.");
                    break;
            }
        }
    }

    public static void ShowDOM(Element element, int depth) {

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

    public static Document readXML(String path) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(path);
        return document;
    }

    public static void readJSON(String path) {

    }

    public static String XMLtoJSON(String xmlText) {
        return XML.toJSONObject(xmlText).toString();
    }

    public static String JSONtoXML(String jsonText) {
        JSONObject json = new JSONObject(jsonText);
        String xml = XML.toString(json);

        return xml;
    }

    public static void writeJSON(String json, String path) throws IOException {
        
    }

    public static void writeXML(Document doc, String path) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileWriter(new File(path)), format);
        writer.write(doc);
        writer.close();
    }
}
