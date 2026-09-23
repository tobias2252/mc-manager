package dev.tobiasbieber.mcmanager.core;

import dev.tobiasbieber.mcmanager.io.PathManager;
import dev.tobiasbieber.mcmanager.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

// center class for all services and manager classes
public class AppContext {

    private static final Logger log = LoggerFactory.getLogger(AppContext.class);

    // singleton!
    private static AppContext instance;

    // service registry
    private final Map<Class<?>, Service> serviceRegistry;

    // manager classes
    private PathManager pathManager;

    private AppContext() {
        this.serviceRegistry = new HashMap<>();
    }

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public void registerService(Service service) {
        log.info("Registering service: {}", service.getClass().getSimpleName());
        serviceRegistry.put(service.getClass(), service);
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        return (T) serviceRegistry.get(serviceClass);
    }

    public PathManager getPathManager() {
        if (pathManager == null) {
            pathManager = new PathManager();
        }
        return pathManager;
    }
}
