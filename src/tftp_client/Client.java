package tftp_client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

/**
 *
 * @author Alexandru Cazacu
 */
public class Client {

    private InetAddress ownIp;
    private InetAddress destIp;
    private int ownPort;
    private int destPort;

    // ----------------------------------------------------------------------------------------------------
    /**
     * Sets up a TFTP Client ready to read/write files from the chosen remote
     * destination.
     *
     * @param ownIp Own Ip.
     * @param ownPort Own Port.
     * @param destIp Destination Ip.
     * @param destPort Destination Port.
     *
     */
    public Client(InetAddress ownIp, int ownPort, InetAddress destIp, int destPort) {
        this.ownIp = ownIp;
        this.ownPort = ownPort;
        this.destIp = destIp;
        this.destPort = destPort;
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Tranfers a file from remoteFilePath to localFilePath using the sockets
     * previously set up. If an error packet is received the trasmission is
     * interrupted.
     *
     * @param remoteFilePath Path of the file to read.
     * @param localFilePath Path of the file to write (relative root is project
     * folder).
     * @throws SocketException
     * @throws IOException
     *
     */
    public void ReadFile(String remoteFilePath, String localFilePath) throws SocketException, IOException {

        FileOutputStream fos = new FileOutputStream(localFilePath);

        DatagramSocket ownSocket = new DatagramSocket(ownPort, ownIp);

        // Sends RRQ.
        byte[] requestBytes = new PacketBuilder().GetRequestDatagram(OpCode.ReadRequest, remoteFilePath, "netascii");
        DatagramPacket requestDatagram = new DatagramPacket(requestBytes, requestBytes.length, destIp, destPort);
        ownSocket.send(requestDatagram);
        System.out.println("Trasmission started...");

        while (true) {
            // Receives DATA.
            byte[] returnBytes = new byte[516];
            DatagramPacket returnDatagram = new DatagramPacket(returnBytes, returnBytes.length, destIp, destPort);
            ownSocket.receive(returnDatagram);

            // If is ERROR.
            if (returnDatagram.getData()[1] == OpCode.Error) {
                System.out.println("ERROR: " + new String(Arrays.copyOfRange(returnDatagram.getData(), 4, returnDatagram.getLength())));
                return;
            }

            // Writes received bytes skipping the header.
            fos.write(Arrays.copyOfRange(returnDatagram.getData(), 4, returnDatagram.getLength()));

            // Sends ACK.
            byte[] ackBytes = new PacketBuilder().GetAckDatagram(OpCode.Acknowledgment, returnDatagram.getData()[2], returnDatagram.getData()[3]);
            DatagramPacket ackDatagram = new DatagramPacket(ackBytes, ackBytes.length, destIp, returnDatagram.getPort());
            ownSocket.send(ackDatagram);

            // Stops if it was the last packet.
            if (returnDatagram.getLength() < 516) {
                fos.close();
                System.out.println("Received last packet of lenght " + returnDatagram.getLength() + "B");
                System.out.println("Trasmission ended.");
                return;
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Tranfers a file from localFilePath to remoteFilePath using the sockets
     * previously set up. If an error packet is received the trasmission is
     * interrupted.
     *
     * @param localFilePath Path of the file to read (relative root is project
     * folder).
     * @param remoteFilePath Path of the file to write.
     * @throws SocketException
     * @throws IOException
     *
     */
    public void WriteFile(String localFilePath, String remoteFilePath) throws SocketException, IOException {

        FileInputStream fis = new FileInputStream(localFilePath);

        DatagramSocket ownSocket = new DatagramSocket(ownPort, ownIp);

        // Sends WRQ.
        byte[] requestBytes = new PacketBuilder().GetRequestDatagram(OpCode.WriteRequest, remoteFilePath, "netascii");
        DatagramPacket requestDatagram = new DatagramPacket(requestBytes, requestBytes.length, destIp, destPort);
        ownSocket.send(requestDatagram);
        System.out.println("Trasmission started...");

        while (true) {
            // Receives ACK.
            byte[] returnBytes = new byte[516];
            DatagramPacket returnDatagram = new DatagramPacket(returnBytes, returnBytes.length, destIp, destPort);
            ownSocket.receive(returnDatagram);

            // ERROR.
            if (returnDatagram.getData()[1] == OpCode.Error) {
                System.out.println("ERROR: " + new String(Arrays.copyOfRange(returnDatagram.getData(), 4, returnDatagram.getLength())));
                return;
            }

            // Reads bytes from the file and cut the array if its the last.
            byte[] msg = new byte[512];
            Arrays.fill(msg, (byte) 0);

            for (int i = 0; i < 512; i++) {
                int n = fis.read();

                if (n >= 0) {
                    msg[i] = (byte) n;
                } else {
                    msg = Arrays.copyOfRange(msg, 0, i);
                    break;
                }
            }
            
            // If the last packet has lenght of 512 we have to send a new one with a lenght of < 512.
            if (msg.length == 0) {
                msg = new byte[]{0};
            }

            // Increments #block by 1 as if the 2 bytes were a single short.
            int block1 = returnDatagram.getData()[2];
            int block2 = returnDatagram.getData()[3];

            block2++;

            if (block2 == 0) {
                block1++;
            }

            //short packetCount = (short) ((block1 << 8) + (block2 & 0xFF));
            // Sends DATA.
            byte[] messageBytes = new PacketBuilder().GetDataDatagram(OpCode.Data, (byte) block1, (byte) block2, msg);
            DatagramPacket messageDatagram = new DatagramPacket(messageBytes, messageBytes.length, destIp, returnDatagram.getPort());
            ownSocket.send(messageDatagram);

            // Stops if it was the last packet.
            if (msg.length < 512) {
                fis.close();
                System.out.println("Sent last packet of lenght " + messageDatagram.getLength() + "B");
                System.out.println("Trasmission ended.");
                return;
            }
        }
    }
}
