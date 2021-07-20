package client.network;
import connector.commands.Command;

import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class ClientSender {
    private Scanner scanner;
    private Command command;
    private Socket socket;
    private Formatter formatter;
    private static ClientSender sender;

    {
        socket = Client.getClient().getSocket();
    }

    public ClientSender() {
        try {
            formatter = new Formatter(socket.getOutputStream());
            // initializing formatting type for message
        } catch (IOException e) {
            e.printStackTrace();
        }
        sender = this;
    }

    public void sendMessage(Command command) {
        String commandToString = Command.makeJson(command);
        formatter.format("%s\n", commandToString);
        formatter.flush();
        // -> sending message to server !
    }

    public static ClientSender getSender() {
        return sender;
    }
}
