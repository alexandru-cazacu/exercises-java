package tftp_client;

/**
 *
 * @author Alexandru Cazacu
 */
public class OpCode {

    public static byte ReadRequest = 1;
    public static byte WriteRequest = 2;
    public static byte Data = 3;
    public static byte Acknowledgment = 4;
    public static byte Error = 5;
}
