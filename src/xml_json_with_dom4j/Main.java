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
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Alexandru Cazacu
 */
public class Main {

    /**
     * @param args the command line arguments
     */
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

        Element root = null;
        Element node = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String command = reader.readLine();

            String params[] = command.split("\\s+");

            // help
            if (params[0].equals("help")) {
                System.out.println("add -r <rootName>");
                System.out.println("add -e <nodeName>");
                System.out.println("save <filename>");
                System.out.println("exit");
            }

            else if (params[0].equals("add")) {
                // add -r <rootname>
                if (params[1].equals("-r")) {
                    document.addElement(params[2]);
                }
                
                // add -e <nodename> <newnode>
                if (params[1].equals("-e")) {
                    
                }
                
                // add -a <nodename> <attributeID> <attributeValue>
                if (params[1].equals("-a")) {
                    
                }
            }
            
            // print
            else if (params[0].equals("print")) {               
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer = new XMLWriter(System.out, format);
                writer.write(document );
            }
            
            // walk
            else if (params[0].equals("walk")) {      
                root = document.addElement("places");
                Element place = root.addElement("place").addAttribute("placeId", "place01");
                place.addElement("city").addText("Brescia");
                place.addElement("city").addText("Milano");
                place.addElement("city").addText("Roma");
                place.addElement("city").addText("Napoli");
                treeWalk(document);
            }

            // save <filename>
            else if (params[0].equals("save")) {
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer = new XMLWriter(new FileWriter(new File(params[1])), format);
                writer.write(document);
                writer.close();
            }

            // exit
            else if (params[0].equals("exit")) {
                System.exit(0);
            }
        }
    }
    
    public static void treeWalk(Document document) {
    treeWalk(document.getRootElement());
}

    public static void treeWalk(Element element) {
        for (int i = 0, size = element.nodeCount(); i < size; i++) {
            Node node = element.node(i);
            if (node instanceof Element) {
                System.out.println(node);
                treeWalk((Element) node);
            }
            else {
                //System.out.println(node.asXML());
            }
        }
    }
}
