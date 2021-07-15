package client;

import com.sanityinc.jargs.CmdLineParser;

import java.io.IOException;

public class Main{
    public static void main(String[] args) throws CmdLineParser.OptionException, IOException {
        DataBase dataBase = DataBase.getInstance();
        dataBase.restoreDate();
        StageController.run(args);
    }
}
