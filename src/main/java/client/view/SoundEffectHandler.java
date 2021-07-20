package client.view;

public class SoundEffectHandler extends FatherAudio {
    public SoundEffectHandler(AudioPath audioPath) {
        super(audioPath);
    }

    @Override
    public void play() {
        mediaPlayer.play();
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.setCycleCount(1);
    }

    public static void playSoundEffect(AudioPath audioPath) {
        SoundEffectHandler soundEffectHandler = new SoundEffectHandler(audioPath);
        soundEffectHandler.play();
    }
}
