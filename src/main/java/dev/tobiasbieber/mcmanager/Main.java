package dev.tobiasbieber.mcmanager;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.ExceptionBridge;
import dev.tobiasbieber.mcmanager.service.entity.ResourcePackService;
import dev.tobiasbieber.mcmanager.service.entity.ShaderPackService;
import dev.tobiasbieber.mcmanager.service.entity.WorldService;
import dev.tobiasbieber.mcmanager.ui.MainApplication;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.dialog.ErrorDialog;
import javafx.application.Platform;
import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    // app context init
    private static final AppContext appContext = AppContext.getInstance();

    public static void main(String[] args) {
        try {
            initServices();
            loadFonts();
            startApp(args);
        } catch (Exception e) {
            log.error("Error occurred during app startup", e);
        } finally {
            stopApp();
        }
    }

    // starts main application
    public static void startApp(String[] args) {
        log.info("STARTING APPLICATION");
        ExceptionBridge.setHandler(e -> {
            log.error("Error occurred during app runtime", e);
            ErrorDialog.show(e);
        });
        ExceptionBridge.install();
        MainApplication.launch(MainApplication.class, args);
    }

    // stops ui thread and closes application
    public static void stopApp() {
        log.info("STOPPING APPLICATION");
        Platform.exit();
        System.exit(0);
    }

    private static void determineOS() {
        // TODO add macOS support later
    }

    private static void initServices() {
        log.info("INITIALIZING SERVICES");
        appContext.registerService(new WorldService());
        appContext.registerService(new ResourcePackService());
        appContext.registerService(new ShaderPackService());
    }

    private static void loadFonts() {
        log.info("LOADING FONTS");
        Font.loadFont(Main.class.getResourceAsStream("/font/Minecraftia.ttf"), 12);
    }
}
