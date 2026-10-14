package dev.tobiasbieber.mcmanager.ui.viewmodel.impl;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.domain.entity.impl.World;
import dev.tobiasbieber.mcmanager.ui.viewmodel.EntityViewModel;
import dev.tobiasbieber.mcmanager.util.Formatter;
import javafx.scene.image.Image;

public class WorldViewModel extends EntityViewModel<World> {

    // managers
    private final LangManager langManager = AppContext.getInstance().getLangManager();

    public WorldViewModel(World entity) {
        super(entity);
    }

    public Image getIcon() {
        return entity.getIcon();
    }

    public String getName() {
        return entity.getName();
    }

    public String getVersion() {
        return entity.getVersion();
    }

    public String getLastPlayed() {
        return Formatter.DEFAULT_DATE_TIME.format(entity.getLastPlayed());
    }

    public String getGameType() {
        return langManager.get(entity.getGameType().getLangKey());
    }

    public String getDifficulty() {
        return langManager.get(entity.getDifficulty().getLangKey());
    }

    public String getHardcore() {
        return langManager.get(entity.getHardcore() ? "text.yes" : "text.no");
    }

    public String getCheatsEnabled() {
        return langManager.get(entity.getCheatsEnabled() ? "text.yes" : "text.no");
    }

    public String getSeed() {
        return entity.getSeed().toString();
    }

    public String getPath() {
        return entity.getPath().toString();
    }

    @Override
    public String getFilterText() {
        return getName() + getVersion() + getLastPlayed();
    }
}
