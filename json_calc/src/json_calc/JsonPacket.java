package json_calc;

/**
 *
 * @author 15367519
 */
public class JsonPacket {
    double a;
    double b;
    String op;
    
    public JsonPacket(double a, double b, String op) {
        this.a = a;
        this.b = b;
        this.op = op;
    }
}
