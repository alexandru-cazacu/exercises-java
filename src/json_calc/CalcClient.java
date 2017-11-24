package json_calc;

import com.google.gson.Gson;
import java.io.IOException;
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

    // ----------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        CalcClient app = new CalcClient();
        app.start();
    }

    // ----------------------------------------------------------------------------------------------------
    private static final List<String> ops = new ArrayList<String>();
    private String op;
    float a;
    float b;
    private Socket socket;

    // ----------------------------------------------------------------------------------------------------
    public CalcClient() {
        ops.add("+");
    }

    // ----------------------------------------------------------------------------------------------------
    private void welcome() {
        System.out.println("Benvenuto in JSONCALC 0.1 - Calcolatrice Client Server");
    }

    // ----------------------------------------------------------------------------------------------------
    private void get_a() {
        System.out.print("a = ");
        Scanner sc = new Scanner(System.in);
        a = sc.nextFloat();
    }

    // ----------------------------------------------------------------------------------------------------
    private void get_b() {
        System.out.print("b = ");
        Scanner sc = new Scanner(System.in);
        b = sc.nextFloat();
    }

    // ----------------------------------------------------------------------------------------------------
    private void get_result() {
        Gson g = new Gson();
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

    // ----------------------------------------------------------------------------------------------------
    public void start() {
        welcome();
        System.out.print("Operazione (+, -, *, /): ");
        Scanner sc = new Scanner(System.in);
        op = sc.next();
        System.out.println(op);

        switch (op) {
            case "+":
                get_a();
                get_b();
                get_result();

        }
        get_result();
    }

    class Op {

        private String symbol;
        private int arity;
    }

    class Op2Arity extends Op {

    }
}
