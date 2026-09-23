package dev.tobiasbieber.mcmanager.domain.entity.impl;

import dev.tobiasbieber.mcmanager.domain.entity.BaseEntity;
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
    private Boolean allowCommands;
    private Long seed;

    public enum GameType {
        SURVIVAL,
        CREATIVE,
        ADVENTURE,
        SPECTATOR
    }

    public enum Difficulty {
        PEACEFUL,
        EASY,
        NORMAL,
        HARD
    }

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

    public Boolean getAllowCommands() {
        return allowCommands;
    }

    public void setAllowCommands(Boolean allowCommands) {
        this.allowCommands = allowCommands;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    // just for testing
    @Override
    public String toString() {
        return "World{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", lastPlayed=" + lastPlayed +
                ", gameType=" + gameType +
                ", difficulty=" + difficulty +
                ", hardcore=" + hardcore +
                ", allowCommands=" + allowCommands +
                ", seed=" + seed +
                '}';
    }
}
