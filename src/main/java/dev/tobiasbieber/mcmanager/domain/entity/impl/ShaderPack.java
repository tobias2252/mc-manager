package dev.tobiasbieber.mcmanager.domain.entity.impl;

import dev.tobiasbieber.mcmanager.domain.entity.BaseEntity;

import java.nio.file.Path;

public class ShaderPack extends BaseEntity {

    private String name;

    public ShaderPack(Path path) {
        super(path);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFilterText() {
        return name + getPath().toString();
    }

    // just for testing
    @Override
    public String toString() {
        return "ShaderPack{" +
                "name='" + name + '\'' +
                '}';
    }
}
