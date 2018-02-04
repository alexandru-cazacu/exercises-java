package tftp_client;

import java.util.ArrayList;

/**
 *
 * @author Alexandru Cazacu
 */
public class PacketBuilder {

    private ArrayList<Byte> packet;

    // ----------------------------------------------------------------------------------------------------
    public PacketBuilder() {
        packet = new ArrayList();
    }

    // ----------------------------------------------------------------------------------------------------
    private PacketBuilder AddInt(int n) {
        packet.add((byte) n);

        return this;
    }

    // ----------------------------------------------------------------------------------------------------
    private PacketBuilder AddByte(byte b) {
        packet.add(b);

        return this;
    }

    // ----------------------------------------------------------------------------------------------------
    private PacketBuilder AddString(String s) {
        byte[] b = s.getBytes();

        for (int i = 0; i < b.length; i++) {
            packet.add(b[i]);
        }

        return this;
    }

    // ----------------------------------------------------------------------------------------------------
    private byte[] GetByteArray() {
        byte[] result = new byte[packet.size()];

        for (int i = 0; i < packet.size(); i++) {
            result[i] = packet.get(i);
        }

        return result;
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Returns byte[] representing an RRQ/WRQ TFTP Packet with the chosen values.
     * 
     * @param opcode Opcode
     * @param filename Filename
     * @param mode Mode "netascii" "octet" "mail"
     * @return byte[] representing the packet
     */
    public byte[] GetRequestDatagram(int opcode, String filename, String mode) {
        packet = new ArrayList();

        AddInt(0).AddInt(opcode).AddString(filename).AddInt(0).AddString(mode).AddInt(0);

        return GetByteArray();
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Returns byte[] representing a DATA TFTP Packet with the chosen values.
     * 
     * @param opcode Opcode
     * @param block1 Packet number (1st half of a short)
     * @param block2 Packet number (2nd half of a shor)
     * @param data Data
     * @return byte[] representing the packet
     */
    public byte[] GetDataDatagram(int opcode, byte block1, byte block2, byte[] data) {
        packet = new ArrayList();

        AddInt(0).AddInt(opcode).AddByte(block1).AddByte(block2);

        for (byte b : data) {
            AddByte(b);
        }

        return GetByteArray();
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Returns byte[] representing an ACK TFTP Packet with the chosen values.
     * 
     * @param opcode Opcode
     * @param block1 Packet number (1st half of a short)
     * @param block2 Packet number (2nd half of a shor)
     * @return byte[] representing the packet
     */
    public byte[] GetAckDatagram(int opcode, int block1, int block2) {
        packet = new ArrayList();

        AddInt(0).AddInt(opcode).AddByte((byte) block1).AddByte((byte) block2);

        return GetByteArray();
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Returns byte[] representing an ERROR TFTP Packet with the chosen values.
     * 
     * @param opcode Opcode
     * @param errCode ErrorCode
     * @param errMsg ErrorMessage
     * @return byte[] representing the packet
     */
    public byte[] GetErrorDatagram(int opcode, int errCode, String errMsg) {
        packet = new ArrayList();

        AddInt(0).AddInt(opcode).AddInt(0).AddInt(errCode).AddString(errMsg);

        return GetByteArray();
    }
}
