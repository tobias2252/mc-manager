package dev.tobiasbieber.mcmanager.domain.enums;

public enum GameType {

    SURVIVAL("gameType.survival"),
    CREATIVE("gameType.creative"),
    ADVENTURE("gameType.adventure"),
    SPECTATOR("gameType.spectator");

    private final String langKey;

    GameType(String langKey) {
        this.langKey = langKey;
    }

    public String getLangKey() {
        return langKey;
    }
}
