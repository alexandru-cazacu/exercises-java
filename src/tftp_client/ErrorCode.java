package tftp_client;

/**
 *
 * @author Alexandru Cazacu
 */
public class ErrorCode {

    public static byte ReadRequest = 0;
    public static byte FileNotFound = 1;
    public static byte AcceessViolation = 2;
    public static byte DiskFull = 3;
    public static byte IllegalOperation = 4;
    public static byte UnknownTransferID = 5;
    public static byte FileAlreadyExists = 6;
    public static byte NoSuchUser = 7;
}
