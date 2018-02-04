package tftp_client;

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
