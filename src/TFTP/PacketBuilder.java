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
package TFTP;

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
