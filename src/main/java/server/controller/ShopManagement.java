package server.controller;

public class ShopManagement extends Controller{
   private static ShopManagement instance;

    public static ShopManagement getInstance() {
        if (instance == null) instance = new ShopManagement();
        return instance;
    }

    @Override
    public String run(String command) {
        return null;
    }
}
