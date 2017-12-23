package xml_json_with_dom4j;

/**
 *
 * @author Alexandru Cazacu
 */
public class Parser {

    // Takes a string and outputs an array of args.
    public static String[] parse(String s) {
        String args[] = s.split("\\s+");

        return args;
    }
}
