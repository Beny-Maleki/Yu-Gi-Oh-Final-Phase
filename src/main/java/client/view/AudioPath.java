package client.view;

public enum AudioPath {
    MAIN_MENU("mainMenu.mp3"),
    IN_Duel("inDuel.mp3"),
    INITIAL_MENUS("initialMenus.mp3"),
    ITS_TIME_TO_DUEL("its-time-to-duel.mp3"),
    LOSE("lose.mp3"),
    WIN("win.mp3"),
    SUMMON_SET("summon.mp3"),
    ATTACK("attack.mp3"),
    CLICK_BUTTON("buttonClick.mp3");

    public final String value;

    AudioPath(String value) {
        this.value = "sounds/" + value;
    }
}
