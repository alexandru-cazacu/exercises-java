package xml_json_with_dom4j;

/**
 *
 * @author Alexandru Cazacu
 */
public class Option {

    private boolean isMandatory;
    private String shortName;
    private String longName;
    private int paramCount;

    public Option(boolean isMandatory, String shortName, String longName, int paramCount) {
        this.isMandatory = isMandatory;
        this.shortName = shortName;
        this.longName = longName;
        this.paramCount = paramCount;
    }

    public boolean getIsMandatory() {
        return isMandatory;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public int getParamCount() {
        return paramCount;
    }
}
