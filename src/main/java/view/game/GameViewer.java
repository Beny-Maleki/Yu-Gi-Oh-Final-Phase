package view.game;

import com.sanityinc.jargs.CmdLineParser;
import controller.ImportScanner;
import controller.gamecontrollers.gamestagecontroller.HeadController;
import model.gameprop.GameInProcess;
import view.Regex;

import java.io.FileNotFoundException;

public class GameViewer {
    public static void run() throws CmdLineParser.OptionException, FileNotFoundException {
        HeadController headController = new HeadController();
        String command;
        String output;
        System.out.println("press START to start game ");
        while (true) {
            command = ImportScanner.getInput();
            if (!GameInProcess.getGame().isGameFinished()) {
                if (isCommandValid(command)) {
                    if ((output = headController.run(command)) != null) {
                        System.out.println(output);
                    }
                } else {
                    System.out.println("invalid input");
                }
            } else {
                break;
            }
        }
    }

    private static boolean isCommandValid(String command) {
        for (String[] phaseCommand : Regex.allGamePlayCommands) {
            for (String patternOfCommand : phaseCommand) {
                if (command.matches(patternOfCommand)) {
                    return true;
                }
            }
        }
        for (String patternOfCommand : Regex.sideStageCommand) {
            if (command.matches(patternOfCommand)) {
                return true;
            }
        }
        for (String patternOfCommand : Regex.generalCommands) {
            if (command.matches(patternOfCommand)) {
                return true;
            }
        }
        return false;
    }
}
