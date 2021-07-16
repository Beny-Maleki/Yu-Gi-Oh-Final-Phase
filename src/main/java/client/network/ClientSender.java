package client.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Connector.commands.Command;

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
        sender = this;
        try {
            formatter = new Formatter(socket.getOutputStream());
            // initializing formatting type for message
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(Command command) {
        Gson gson = new GsonBuilder().create();
        String commandToString = gson.toJson(command, Command.class);
        formatter.format("%s\n", commandToString);
        formatter.flush();
        // -> sending message to server !
    }

    public static ClientSender getSender() {
        return sender;
    }
}
