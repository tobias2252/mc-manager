package dev.tobiasbieber.mcmanager.domain.entity.impl;

import dev.tobiasbieber.mcmanager.domain.entity.BaseEntity;
import dev.tobiasbieber.mcmanager.domain.enums.Difficulty;
import dev.tobiasbieber.mcmanager.domain.enums.GameType;
import javafx.scene.image.Image;

import java.nio.file.Path;
import java.time.Instant;

public class World extends BaseEntity {

    private Image icon;
    private String name;
    private String version;
    private Instant lastPlayed;
    private GameType gameType;
    private Difficulty difficulty;
    private Boolean hardcore;
    private Boolean cheatsEnabled;
    private Long seed;

    public World(Path path) {
        super(path);
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Instant getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(Instant lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Boolean getHardcore() {
        return hardcore;
    }

    public void setHardcore(Boolean hardcore) {
        this.hardcore = hardcore;
    }

    public Boolean getCheatsEnabled() {
        return cheatsEnabled;
    }

    public void setCheatsEnabled(Boolean cheatsEnabled) {
        this.cheatsEnabled = cheatsEnabled;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }
}
