package server.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Controller {


    public abstract String run(String command);

    public Matcher matcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }
}
