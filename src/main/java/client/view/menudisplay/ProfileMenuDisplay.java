package client.view.menudisplay;

import client.model.enums.Error;
import client.model.enums.MenusMassages.Profile;


public class ProfileMenuDisplay {
    public static void display(Profile message) {
            System.out.println(message.getMessage());

    }

    public static void display(Error message) {
            System.out.println(message.toString());
    }

    public static void display(Profile message, String name) {
            System.out.printf(message.getMessage(), name);
            System.out.println();

    }

    public static void display(Error message, String name) {
            System.out.printf(message.toString(), name);
            System.out.println();

    }
}
