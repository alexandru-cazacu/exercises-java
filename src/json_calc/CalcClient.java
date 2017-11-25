package json_calc;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 15367519
 */
public class CalcClient {

    public static void main(String[] args) {
        CalcClient client = new CalcClient();
        client.start();
    }

    private static List<String> validOperations = new ArrayList<String>();

    // ----------------------------------------------------------------------------------------------------
    public CalcClient() {
        validOperations.add("+");
        validOperations.add("-");
        validOperations.add("*");
        validOperations.add("/");
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Takes in input a number from the user and validates it.
     *
     * @param operandName What to prepend before asking the user.
     * @return Double chosen by the user.
     */
    private double getOperand(String operandName) {

        while (true) {
            System.out.print(operandName + ": ");
            Scanner sc = new Scanner(System.in);
            String inputValue = null;

            try {
                inputValue = sc.next();
                return Double.parseDouble(inputValue);
            }
            catch (NumberFormatException ex) {
                System.out.println("`" + inputValue + "` is not a valid operand, try again.");
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Takes in input an operand from the user and validates it.
     *
     * @param operationName What to prepend before ascking the user.
     * @return String of the operation chosen by the user.
     */
    private String getOperation(String operationName) {

        while (true) {
            System.out.print(operationName + ": ");
            Scanner sc = new Scanner(System.in);
            String inputValue = sc.next();

            if (validOperations.contains(inputValue)) {
                return inputValue;
            }
            else {
                System.out.println("`" + inputValue + "` is not a valid operation, try again.");
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Asks a server to do the calculation chosen by the user. Prints the
     * result.
     *
     * @param op1 First operand
     * @param op2 Second operand
     * @param operation Operation
     */
    private void getResult(double op1, double op2, String operation) {
        Gson gson = new Gson();
        JsonPacket jsonPacket = new JsonPacket(op1, op2, operation);
        System.out.println("User input resulted in: " + gson.toJson(jsonPacket));

        InetAddress inetAddress;
        try {
            // Sets up a socket for the server.
            inetAddress = InetAddress.getByName("127.0.0.1");
            Socket socket = new Socket(inetAddress, 3000);
            System.out.println("InetAddress: " + inetAddress);

            // Sends the json containing our request to the server.
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(gson.toJson(jsonPacket));

            // Gets the result from the server.
            BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine = inStream.readLine();
            // Creates a class from the json.
            JsonResultPacket resultPacket = gson.fromJson(inputLine, JsonResultPacket.class);

            System.out.println("Result: " + resultPacket.result);

            printWriter.close();
        }

        catch (IOException ex) {
            Logger.getLogger(CalcClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ----------------------------------------------------------------------------------------------------
    public void start() {
        System.out.println("Benvenuto in JSONCALC 0.1 - Calcolatrice Client Server");

        double a = getOperand("First operand");
        double b = getOperand("Second operand");
        String op = getOperation("Operation");

        getResult(a, b, op);
    }
}
