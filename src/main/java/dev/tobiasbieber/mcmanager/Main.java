package dev.tobiasbieber.mcmanager;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.ui.MainApplication;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    // app context init
    private static final AppContext appContext = AppContext.getInstance();

    public static void main(String[] args) {
        try {
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
        MainApplication.launch(MainApplication.class, args);
    }

    // stops ui thread and closes application
    public static void stopApp() {
        log.info("STOPPING APPLICATION");
        Platform.exit();
        System.exit(0);
    }
}
