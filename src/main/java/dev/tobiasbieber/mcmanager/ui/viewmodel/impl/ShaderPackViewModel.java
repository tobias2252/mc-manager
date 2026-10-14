package dev.tobiasbieber.mcmanager.ui.viewmodel.impl;

import dev.tobiasbieber.mcmanager.domain.entity.impl.ShaderPack;
import dev.tobiasbieber.mcmanager.ui.viewmodel.EntityViewModel;

public class ShaderPackViewModel extends EntityViewModel<ShaderPack> {

    public ShaderPackViewModel(ShaderPack entity) {
        super(entity);
    }

    public String getName() {
        return entity.getName();
    }

    public String getPath() {
        return entity.getPath().toString();
    }

    @Override
    public String getFilterText() {
        return getName();
    }
}
