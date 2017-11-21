package xml_json_with_dom4j;

import java.util.ArrayList;

/**
 *
 * @author Alexandru Cazacu
 */
public class Command {

    private String name;
    private ArrayList<Option> options;

    public Command(String name) {
        this.name = name;
        this.options = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

}
