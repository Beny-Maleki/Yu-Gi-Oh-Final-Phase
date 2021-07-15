package client.view.menudisplay;

import client.model.enums.rockpaperscissor.PaperRockScissorError;
import client.model.enums.rockpaperscissor.RockPaperScissorMessage;

public class RockPaperScissorDisplay {
    public static  void display(RockPaperScissorMessage message){
        System.out.println(message.toString());
    }
    public static void display(PaperRockScissorError error){
        System.out.println(error.toString());
    }
    public static void display(RockPaperScissorMessage message , String playerName){
        System.out.printf(message.toString(), playerName);
        System.out.println();
    }
}
