package dev.tobiasbieber.mcmanager.ui.model.impl;

import dev.tobiasbieber.mcmanager.domain.entity.impl.ResourcePack;
import dev.tobiasbieber.mcmanager.ui.model.EntityViewModel;
import dev.tobiasbieber.mcmanager.util.Formatter;
import javafx.scene.image.Image;

public class ResourcePackViewModel extends EntityViewModel<ResourcePack> {

    public ResourcePackViewModel(ResourcePack entity) {
        super(entity);
    }

    public Image getIcon() {
        return entity.getIcon();
    }

    public String getName() {
        return getEntity().getName();
    }

    public String getDescription() {
        return Formatter.stripMinecraftFormatting(getEntity().getDescription());
    }

    public String getPath() {
        return getEntity().getPath().toString();
    }

    @Override
    public String getFilterText() {
        return getName() + getDescription();
    }
}
