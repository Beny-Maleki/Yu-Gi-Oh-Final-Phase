package client.controller.gamecontrollers.gamestagecontroller;

import com.sanityinc.jargs.CmdLineParser;
import client.controller.gamecontrollers.GeneralController;

public class StandByPhaseController extends GeneralController {

    private static StandByPhaseController instance;

    public StandByPhaseController(){}

    public static StandByPhaseController getInstance() {
        if (instance == null) instance = new StandByPhaseController() ;
        return instance;
    }

    public String run(String command) throws CmdLineParser.OptionException {
        return null;
    }
}
