package client;

import client.network.Client;
import client.network.ClientSender;
import com.sanityinc.jargs.CmdLineParser;

import java.io.IOException;

public class Main{
    public static void main(String[] args) throws CmdLineParser.OptionException, IOException {
        Client client = new Client();
        client.startGetResponseFromServer();
        new ClientSender();
        StageController.run(args);
    }
}
