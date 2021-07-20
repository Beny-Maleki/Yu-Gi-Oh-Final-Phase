package client.view;

public class ClickButtonHandler extends SoundEffectHandler{
    private static ClickButtonHandler instance;

    public ClickButtonHandler(AudioPath audioPath) {
        super(audioPath);
    }

    public static ClickButtonHandler getInstance() {
        if (instance == null) {
            instance = new ClickButtonHandler(AudioPath.CLICK_BUTTON);
        }
        return instance;
    }
}
