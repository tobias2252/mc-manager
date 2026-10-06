package dev.tobiasbieber.mcmanager.domain.entity.impl;

import dev.tobiasbieber.mcmanager.domain.entity.BaseEntity;
import javafx.scene.image.Image;

import java.nio.file.Path;

public class ResourcePack extends BaseEntity {

    private Image icon;
    private String name;
    private String description;

    public ResourcePack(Path path) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getFilterText() {
        return name + description + getPath().toString();
    }

    // just for testing
    @Override
    public String toString() {
        return "ResourcePack{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
