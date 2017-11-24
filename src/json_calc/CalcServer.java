package json_calc;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 15367519
 */
public class CalcServer {

    public static void main(String[] args) {
        CalcServer server = new CalcServer();
        server.run();
    }

    ServerSocket serverSocket;
    Socket clientSocket;

    public CalcServer() {

        try {
            this.serverSocket = new ServerSocket(19999);
        } catch (IOException ex) {
            Logger.getLogger(CalcServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // ----------------------------------------------------------------------------------------------------
    public void run() {
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();

                processClientRequest(clientSocket);
            } catch (Exception e) {
                System.err.println("Error during server execution: " + e);
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------
    private void processClientRequest(Socket clientSocket) throws IOException {
        Gson g = new Gson();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        OutputStream output = clientSocket.getOutputStream();
        long time = System.currentTimeMillis();

        String inputLine = null;
//        while (null != (inputLine = in.readLine())) {
//            System.out.println(inputLine);
//        }

        while (inputLine == null) {
            inputLine = in.readLine();
            System.out.println("inputLine: " + inputLine);
        }

        JsonPacket packet = g.fromJson(inputLine, JsonPacket.class);
        // output.write(responseDocument);

        output.close();
        in.close();
        System.out.println("Request processed: " + time);

        if (packet.op.equals("+")) {
            System.out.println("Sum: " + (packet.a + packet.b));
        }

        JsonPacket packt = new JsonPacket(a, b, op);
        System.out.println(g.toJson(packt));

        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName("127.0.0.1");

            socket = new Socket(inetAddress, 19999);

            System.out.println("InetAddress: " + inetAddress);

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(g.toJson(packt));
            printWriter.close();

        } catch (IOException ex) {
            Logger.getLogger(CalcClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
