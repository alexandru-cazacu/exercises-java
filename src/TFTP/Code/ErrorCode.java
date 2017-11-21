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
package TFTP.Code;

/**
 *
 * @author Alexandru Cazacu
 */
public class ErrorCode {

    public static byte ReadRequest = 0;
    public static byte FileNotFound = 1;
    public static byte AcceessViolation = 2;
    public static byte DiskFull = 3;
    public static byte IllegalOperation = 4;
    public static byte UnknownTransferID = 5;
    public static byte FileAlreadyExists = 6;
    public static byte NoSuchUser = 7;
}
