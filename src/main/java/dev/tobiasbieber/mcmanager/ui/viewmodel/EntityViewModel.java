package dev.tobiasbieber.mcmanager.ui.viewmodel;

import dev.tobiasbieber.mcmanager.domain.entity.BaseEntity;

public abstract class EntityViewModel<T extends BaseEntity> {

    protected T entity;

    public abstract String getFilterText();

    public EntityViewModel(T entity) {
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }
}
