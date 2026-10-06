package dev.tobiasbieber.mcmanager.service.entity;

import dev.tobiasbieber.mcmanager.domain.entity.impl.ResourcePack;
import dev.tobiasbieber.mcmanager.io.entity.impl.ResourcePackStorage;
import dev.tobiasbieber.mcmanager.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ResourcePackService implements Service {

    private static final Logger log = LoggerFactory.getLogger(ResourcePackService.class);

    private final ResourcePackStorage storage;

    private List<ResourcePack> resourcePacks;

    public ResourcePackService() {
        storage = new ResourcePackStorage();
        resourcePacks = new ArrayList<>();
    }

    // loads all resource packs from the installation directory
    public void loadResourcePacks() {
        log.info("Loading resource packs...");
        resourcePacks = storage.load();
    }

    // returns a copy of the resource pack list
    public List<ResourcePack> getResourcePacks() {
        return new ArrayList<>(resourcePacks);
    }

    public void deleteResourcePack(ResourcePack resourcePack) {
        log.info("Deleting resource pack: {}", resourcePack.getName());
        storage.delete(resourcePack);
        resourcePacks.remove(resourcePack);
    }
}
