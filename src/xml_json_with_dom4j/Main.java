package xml_json_with_dom4j;

import java.io.IOException;
import org.dom4j.DocumentException;

/**
 *
 * @author Alexandru Cazacu
 */
public class Main {

    
    // ----------------------------------------------------------------------------------------------------
    public static void main(String[] args) throws IOException, DocumentException {

        DomBuilder domBuilder = new DomBuilder();
        
        /* The dom is represented by a tree. You can add elements with the first set of commands.
         * Every command is executed on the current node. You can change the current node using
         * the second set of istructions.
         */
        while (true) {
            String[] arguments = Parser.parse(ConsoleReader.Read());

            // Adds a new node, attribute or text.
            if (arguments[0].equals("add")) {
                // add -n nodeName (adds a new node).
                if (arguments[1].equals("-n")) {
                    domBuilder.addNode(arguments[2]);
                }
                // add -a attributeName attributeValue (adds a new attribute with a value to the current node).
                if (arguments[1].equals("-a")) {
                    domBuilder.addAttribute(arguments[2], arguments[3]);
                }
                // add -t textValue (adds a new inner text to the current node).
                if (arguments[1].equals("-t")) {
                    domBuilder.addText(arguments[2]);
                }
                // add -r rootName (adds a new root node).
                if (arguments[1].equals("-r")) {
                    domBuilder.addRoot(arguments[2]);
                }
            }

            // Changes the current node.
            if (arguments[0].equals("cn")) {
                // cn -u (chages current node to previous node, on the same level).
                if (arguments[1].equals("-u")) {
                    domBuilder.moveUp();
                }
                // cn -d (changes current node to next node, on the same level).
                if (arguments[1].equals("-d")) {
                    domBuilder.moveDown();
                }
                // cn -c (changes current node to child node).
                if (arguments[1].equals("-c")) {
                    domBuilder.moveToChild();
                }
                // cn -p (changes current node to parent node).
                if (arguments[1].equals("-p")) {
                    domBuilder.moveToParent();
                }
            }

            // print (prints the current DOM).
            if (arguments[0].equals("print")) {
                domBuilder.Print();
            }

            // Saves on disk.
            if (arguments[0].equals("write")) {
                // write -o outFile.xml
                if (arguments[1].equals("-o")) {
                    domBuilder.writeXML(arguments[2]);
                }
            }

            // Takes an xml and saves a json.
            if (arguments[0].equals("xml-to-json")) {
                // xml-to-json in.xml out.json
                domBuilder.XMLtoJSON(arguments[1], arguments[2]);
            }

            // Takes a json and saves an xml.
            if (arguments[0].equals("json-to-xml")) {
                // json-to-xml in.json out.xml
                domBuilder.JSONtoXML(arguments[1], arguments[2]);
            }

            // clear (clears the DOM).
            if (arguments[0].equals("clear")) {
                domBuilder.clear();
            }

            // exit (exits program)
            if (arguments[0].equals("exit")) {
                System.exit(0);
            }
        }
    }
}
