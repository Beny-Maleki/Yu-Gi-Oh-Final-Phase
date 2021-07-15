package server.controller;

public class DuelManagement extends Controller {
    private static DuelManagement instance;

    public static DuelManagement getInstance() {
        if (instance == null) instance = new DuelManagement();
        return instance;
    }

    @Override
    public String run(String command) {
        return null;
    }
}
