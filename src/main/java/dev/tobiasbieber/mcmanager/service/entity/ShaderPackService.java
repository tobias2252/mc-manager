package dev.tobiasbieber.mcmanager.service.entity;

import dev.tobiasbieber.mcmanager.domain.entity.impl.ShaderPack;
import dev.tobiasbieber.mcmanager.io.entity.impl.ShaderPackStorage;
import dev.tobiasbieber.mcmanager.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ShaderPackService implements Service {

    private static final Logger log = LoggerFactory.getLogger(ShaderPackService.class);

    private final ShaderPackStorage storage;

    private List<ShaderPack> shaderPacks;

    public ShaderPackService() {
        storage = new ShaderPackStorage();
        shaderPacks = new ArrayList<>();
    }

    // loads all shader packs from the installation directory
    public void loadShaderPacks() {
        log.info("Loading shader packs...");
        shaderPacks = storage.load();
    }

    // returns a copy of the shader pack list
    public List<ShaderPack> getShaderPacks() {
        return new ArrayList<>(shaderPacks);
    }

    public void deleteShaderPack(ShaderPack shaderPack) {
        log.info("Deleting shader pack: {}", shaderPack.getName());
        storage.delete(shaderPack);
        shaderPacks.remove(shaderPack);
    }
}
