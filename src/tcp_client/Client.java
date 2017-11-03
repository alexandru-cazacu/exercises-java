/*
 * Copyright (C) 2017 Alexandru Cazacu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
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
