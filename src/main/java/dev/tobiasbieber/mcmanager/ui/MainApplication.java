package dev.tobiasbieber.mcmanager.ui;

import dev.tobiasbieber.mcmanager.core.AppContext;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private static final String appTitle = "mc-manager";
    private static final int initWidth = 1200;
    private static final int initHeight = 800;

    private final SceneManager sceneManager;

    public MainApplication() {
        sceneManager = AppContext.getInstance().getSceneManager();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(appTitle);

        // connect main stage to scene manager
        sceneManager.connectStage(stage);

        stage.setWidth(initWidth);
        stage.setHeight(initHeight);
        stage.show();
    }
}
