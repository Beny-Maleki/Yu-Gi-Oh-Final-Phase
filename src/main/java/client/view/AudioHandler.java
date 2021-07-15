package client.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;


public class AudioHandler {
    private static ArrayList<AudioHandler> audioHandlers;
    private static AudioHandler playing;
    private static AudioPath playingAudioPath;

    static {
        audioHandlers = new ArrayList<>();
        playing = null;
        playingAudioPath = null;
    }

    private String name;
    private MediaPlayer mediaPlayer;

    public AudioHandler(AudioPath audioPath) {
        Media hit = new Media(new File(Paths.get(audioPath.value).toAbsolutePath().toString()).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        playingAudioPath = audioPath;
        audioHandlers.add(this);
    }

    public static AudioPath getPlayingAudioPath() {
        return playingAudioPath;
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

    public static AudioHandler getPlaying() {
        return playing;
    }

    public void play() {
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        playing = this;
    }

    public static void muteThePlaying() {
        playing.getMediaPlayer().setMute(true);
    }

    public static void unmuteThePlaying() {
        playing.getMediaPlayer().setMute(false);
    }


}



