package tcp_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Alexandru Cazacu
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Indirizzo IP di google.it
        Socket destSocket = new Socket("64.233.161.99", 80);

        // Writes on the socket.
        PrintWriter pr = new PrintWriter(destSocket.getOutputStream());
        pr.println("GET / HTTP/1.1\n");
        // Sends the request.
        pr.flush();

        // Return strings.
        BufferedReader br = new BufferedReader(new InputStreamReader(destSocket.getInputStream()));

        String returnString;
        while ((returnString = br.readLine()) != null) {
            System.out.println(returnString);
        }
        destSocket.close();
    }
}
