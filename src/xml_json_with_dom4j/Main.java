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

    // ----------------------------------------------------------------------------------------------------
    public static void main(String[] args) throws IOException, DocumentException {

        Command addCommand = new Command("add");
        addCommand.getOptions().add(new Option(false, "-n", "--node", 1));
        addCommand.getOptions().add(new Option(false, "-a", "--attribute", 1));
        addCommand.getOptions().add(new Option(false, "-t", "--text", 1));
        addCommand.getOptions().add(new Option(false, "-r", "--root", 1));

        Command changeNodeCommand = new Command("cn");
        changeNodeCommand.getOptions().add(new Option(false, "-u", "--up", 0));
        changeNodeCommand.getOptions().add(new Option(false, "-d", "--down", 0));
        changeNodeCommand.getOptions().add(new Option(false, "-c", "--child", 0));
        changeNodeCommand.getOptions().add(new Option(false, "-p", "--parent", 0));

        Command printCommand = new Command("print");
        printCommand.getOptions().add(new Option(false, "", "--xml", 0));

        Command writeCommand = new Command("write");
        writeCommand.getOptions().add(new Option(false, "-o", "--outDir", 1));

        Command clearCommand = new Command("clear");
        Command exitCommand = new Command("exit");

        DomBuilder domBuilder = new DomBuilder();

        while (true) {
            String[] arguments = Parser.parse(ConsoleReader.Read());

            if (arguments[0].equals("add")) {
                if (arguments[1].equals("-n")) {
                    domBuilder.addNode(arguments[2]);
                }
                if (arguments[1].equals("-a")) {
                    domBuilder.addAttribute(arguments[2], arguments[3]);
                }
                if (arguments[1].equals("-t")) {
                    domBuilder.addText(arguments[2]);
                }
                if (arguments[1].equals("-r")) {
                    domBuilder.addRoot(arguments[2]);
                }
            }

            if (arguments[0].equals("cn")) {
                if (arguments[1].equals("-u")) {
                    domBuilder.moveUp();
                }
                if (arguments[1].equals("-d")) {
                    domBuilder.moveDown();
                }
                if (arguments[1].equals("-c")) {
                    domBuilder.moveToChild();
                }
                if (arguments[1].equals("-p")) {
                    domBuilder.moveToParent();
                }
            }

            if (arguments[0].equals("print")) {
                domBuilder.Print();
            }

            if (arguments[0].equals("write")) {
                if (arguments[1].equals("-o")) {
                    domBuilder.writeXML(arguments[2]);
                }
            }

            if (arguments[0].equals("xml-to-json")) {
                domBuilder.XMLtoJSON(arguments[1], arguments[2]);
            }

            if (arguments[0].equals("json-to-xml")) {
                domBuilder.JSONtoXML(arguments[1], arguments[2]);
            }

            if (arguments[0].equals("clear")) {
                domBuilder.clear();
            }

            if (arguments[0].equals("exit")) {
                System.exit(0);
            }
        }
    }
}
