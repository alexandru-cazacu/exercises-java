/*
 * Copyright (C) 2017 Alex
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
package ping_pong_udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Alexandru Cazacu
 */
public class Server {

    public static void main(String[] args) throws UnknownHostException, SocketException, IOException { // Message to send.
        String msg = "Pong";
        // Own ip.
        InetAddress ownIp = InetAddress.getByName("127.0.0.1");
        // Destination ip.
        InetAddress dstIp = InetAddress.getByName("127.0.0.1");
        // Own port.
        int ownPort = 3000;
        // Destination port.
        int dstPort = 3001;

        // Own socket.
        DatagramSocket ownSkt = new DatagramSocket(ownPort, ownIp);

        // Create empty buffer.
        byte[] buf = new byte[256];
        // Sets buffer as return packet buffer.
        DatagramPacket returnPkt = new DatagramPacket(buf, buf.length);
        // Waits for a packet on the port.Continue execution when it receives one.
        ownSkt.receive(returnPkt);
        // Prints own socket info.
        System.out.println("Socket: " + ownIp + ":" + ownPort);

        // Prints sender socket info.
        System.out.println("Sender: " + returnPkt.getAddress() + ":" + returnPkt.getPort());
        // Prints sender message as String.
        System.out.println("Msg: " + new String(returnPkt.getData(), 0, returnPkt.getLength()));

        // Fills DatagramPacket with previously declared msg.
        DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), dstIp, dstPort);
        // Sends DatagramPacket on the socket. ownSkt.send(dp);

        // Quits.
        System.exit(0);
    }
}
