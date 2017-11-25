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
        server.Start();
    }

    private ServerSocket serverSocket;

    public CalcServer() {

        try {
            this.serverSocket = new ServerSocket(3000);
        }
        catch (IOException ex) {
            Logger.getLogger(CalcServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Server ready on port: " + serverSocket.getLocalPort());
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Receives and processes requests.
     */
    public void Start() {
        while (true) {
            System.out.println("Server waiting for a request...");
            try {
                // Blocks.
                Socket clientSocket = this.serverSocket.accept();
                processClientRequest(clientSocket);
            }
            catch (IOException ex) {
                Logger.getLogger(CalcServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Processes the client request and send the result on the same socket.
     *
     * @param clientSocket Client socket to use as i/o
     * @throws IOException
     */
    private void processClientRequest(Socket clientSocket) throws IOException {
        long startRequestTime = System.currentTimeMillis();

        Gson gson = new Gson();

        // Input / output buffers from client.
        BufferedReader inBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream outBuffer = clientSocket.getOutputStream();

        // No need to use a cycle since we know for a fact that a request
        // will always have 1 line.
        String inputLine = inBuffer.readLine();
        // Creates a class from the json.
        JsonPacket jsonInputPacket = gson.fromJson(inputLine, JsonPacket.class);

        // Calculates result.
        double result = 0;
        switch (jsonInputPacket.op) {
            case "+":
                result = jsonInputPacket.a + jsonInputPacket.b;
                break;
            case "-":
                result = jsonInputPacket.a - jsonInputPacket.b;
                break;
            case "*":
                result = jsonInputPacket.a * jsonInputPacket.b;
                break;
            case "/":
                result = jsonInputPacket.a / jsonInputPacket.b;
                break;
            default:
                break;
        }

        // Sends result to client.
        PrintWriter printWriter = new PrintWriter(outBuffer, true);
        printWriter.println(gson.toJson(new JsonResultPacket(result)));

        inBuffer.close();
        outBuffer.close();
        printWriter.close();

        System.out.println("Request processed in " + (System.currentTimeMillis() - startRequestTime) / 1000.0f + "s");
        System.out.println("Processed result was: " + result);
    }
}
