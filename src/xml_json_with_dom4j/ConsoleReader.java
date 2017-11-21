package xml_json_with_dom4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Alexandru Cazacu
 */
public class ConsoleReader {

    public static String Read() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = reader.readLine();
        
        return command;
    }
}
