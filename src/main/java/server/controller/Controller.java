package server.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Controller {
    public abstract String run(String command);

    public Matcher matcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }
}
