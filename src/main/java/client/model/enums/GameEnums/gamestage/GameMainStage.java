package client.model.enums.GameEnums.gamestage;

public enum GameMainStage {
    DRAW_PHASE(1, "draw phase"),
    STAND_BY_PHASE(2, "stand by phase"),
    FIRST_MAIN_PHASE(3, "first main phase"),
    BATTLE_PHASE(4, "battle phase"),
    SECOND_MAIN_PHASE(5, "second main phase"),
    END_PHASE(6, "end phase");

    int phaseNumber;
    String phaseName;

    GameMainStage(int phaseNumber, String phaseName) {
        this.phaseNumber = phaseNumber;
        this.phaseName = phaseName;
    }

    private static GameMainStage getStageByNumber(int phaseNumber) {
        switch (phaseNumber) {
            case 1:
            case 7:
                return DRAW_PHASE;
            case 2:
                return STAND_BY_PHASE;
            case 3:
                return FIRST_MAIN_PHASE;
            case 4:
                return BATTLE_PHASE;
            case 5:
                return SECOND_MAIN_PHASE;
            case 6:
                return END_PHASE;
        }
        return null;
    }

    public static GameMainStage getNextPhase(int currentStage) {
        return getStageByNumber(currentStage + 1);
    }

    public String getPhaseName() {
        return phaseName;
    }

    public int getPhaseNumber() {
        return phaseNumber;
    }
}
