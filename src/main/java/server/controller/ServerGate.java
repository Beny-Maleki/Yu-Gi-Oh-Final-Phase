package server.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class ServerGate {
    public static void initialize() {
        try {
            ServerSocket serverSocket = new ServerSocket(7755);
            System.out.println("server is on and listening...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("A client requested to connect...");

                serviceClient(socket, serverSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serviceClient(Socket socket, ServerSocket serverSocket) {
        new Thread(() -> {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                analyzeRequestAndRespond(dataInputStream, dataOutputStream);
                dataInputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void analyzeRequestAndRespond(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        while (true) try {
            String command;
            try {
                command = dataInputStream.readUTF();
            } catch (SocketException exception) {
                System.out.println("A client disconnected");
                break;
            }

            String result = checkCommand(command); // finding the related branch to Command

            dataOutputStream.writeUTF(result);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String checkCommand(String command) {
        CommandBranch[] branches = CommandBranch.values();
        for (CommandBranch branch : branches) {
            if (command.startsWith(branch.getVal())) {
               branch.getInstance().run(command);
            }
        }
        return "invalid command";
    }

}
