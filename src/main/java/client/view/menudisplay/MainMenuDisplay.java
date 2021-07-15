package client.view.menudisplay;

import client.model.enums.Error;
import client.model.enums.MenusMassages.Main;

public class MainMenuDisplay {
    public static void display(Main message) {
        System.out.println(message.getMainMessage());
    }

    public static void display(Main message, String field1, String field2) {
        System.out.printf(message.getMainMessage(), field1, field2);
    }

    public static void display(Error message) {
        System.out.println(message.toString());
    }
}
