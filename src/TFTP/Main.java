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

import ezconfig.Configurator;
import ezconfig.IniConfigurator;
import ezconfig.error.SectionNotExistingException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Alexandru Cazacu
 */
public class Main {

    public static void main(String[] args) throws UnknownHostException, IOException, SectionNotExistingException {

        IniConfigurator iniConfig = new IniConfigurator();

        Configurator config = iniConfig.Read("config.ini");

        // Gets settings.
        String serverIp = config.GetSection("Server").GetParam("serverIp").getValue();
        String clientIp = config.GetSection("Client").GetParam("clientIp").getValue();
        String clientRoot = config.GetSection("Client").GetParam("root").getValue();
        int serverPort = Integer.parseInt(config.GetSection("Server").GetParam("serverPort").getValue());
        int clientPort = Integer.parseInt(config.GetSection("Client").GetParam("clientPort").getValue());

        /* Use this if you dont have the Configurator JAR
        String serverIp = "127.0.0.1";
        String clientIp = "127.0.0.1";
        String clientRoot = "C:/Users/Alex/Downloads/";
        int serverPort = 69;
        int clientPort = 3000;
        */
        
        Client client = new Client(InetAddress.getByName(clientIp), clientPort, InetAddress.getByName(serverIp), serverPort);

//        client.ReadFile(clientRoot + "original.txt", "result.txt");
        client.WriteFile(clientRoot + "original.txt", "result.txt");

    }
}
