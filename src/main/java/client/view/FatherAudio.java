package client.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FatherAudio {
    protected static ArrayList<FatherAudio> audioHandlers;

    static {
        audioHandlers = new ArrayList<>();
    }

    protected String name;
    protected MediaPlayer mediaPlayer;

    public FatherAudio(AudioPath audioPath) {
        Media hit = new Media(new File(Paths.get(audioPath.value).toAbsolutePath().toString()).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        audioHandlers.add(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void play() {
    }
}
