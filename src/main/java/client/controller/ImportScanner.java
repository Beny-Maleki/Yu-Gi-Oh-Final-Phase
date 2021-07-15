package client.controller;

import java.util.Scanner;

public class ImportScanner {
    private static final Scanner INPUT = new Scanner(System.in);

    public static String getInput() {
        return INPUT.nextLine().trim();
    }
}
