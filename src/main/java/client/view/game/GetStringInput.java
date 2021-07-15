package client.view.game;

import client.model.enums.GameEnums.RequestingInput;

import java.util.Scanner;

public class GetStringInput {
    public static String getInput(RequestingInput message) {
        System.out.println(message.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String getInput(RequestingInput message, String name) {
        System.out.println(message.toString() + name);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
