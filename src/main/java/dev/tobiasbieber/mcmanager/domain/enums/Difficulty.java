package dev.tobiasbieber.mcmanager.domain.enums;

public enum Difficulty {

    PEACEFUL("difficulty.peaceful"),
    EASY("difficulty.easy"),
    NORMAL("difficulty.normal"),
    HARD("difficulty.hard");

    private final String langKey;

    Difficulty(String langKey) {
        this.langKey = langKey;
    }

    public String getLangKey() {
        return langKey;
    }
}
