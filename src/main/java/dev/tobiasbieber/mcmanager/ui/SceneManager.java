package dev.tobiasbieber.mcmanager.ui;

import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity.ModPane;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity.ResourcePackPane;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity.ShaderPackPane;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity.WorldPane;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.other.SettingsPane;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.shared.MenuPane;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SceneManager {

    private static final Logger log = LoggerFactory.getLogger(SceneManager.class);

    private final BorderPane rootPane;
    private final StackPane mainPane;

    private Stage stage;
    private BaseUI<?, ?> currentUI;

    // shared ui objects
    public MenuPane.UI menuPaneUI;

    // entity ui objects
    public WorldPane.UI worldPaneUI;
    public ModPane.UI modPaneUI;
    public ResourcePackPane.UI resourcePackPaneUI;
    public ShaderPackPane.UI shaderPackPaneUI;

    // other ui objects
    public SettingsPane.UI settingsPaneUI;

    public SceneManager() {
        rootPane = new BorderPane();
        mainPane = new StackPane();

        // menu pane
        menuPaneUI = new MenuPane.UI();
        rootPane.setLeft(menuPaneUI.getRoot());

        // entity ui objects
        worldPaneUI = new WorldPane.UI();
        modPaneUI = new ModPane.UI();
        resourcePackPaneUI = new ResourcePackPane.UI();
        shaderPackPaneUI = new ShaderPackPane.UI();

        // other ui objects
        settingsPaneUI = new SettingsPane.UI();

        showUI(worldPaneUI, WorldPane.class); // world pane is shown first
        rootPane.setCenter(mainPane);
    }

    // shows the given ui object
    // executes the afterShow and beforeShow methods
    public void showUI(BaseUI<?, ?> ui, Class<?> uiClass) {
        if (!ui.equals(currentUI)) {
            log.info("Showing UI: {}", uiClass.getSimpleName());
            if (currentUI != null) {
                currentUI.getController().afterShow();
            }
            ui.getController().beforeShow();
            mainPane.getChildren().setAll(ui.getRoot());
            currentUI = ui;
        }
    }

    // connects a stage to the scene manager
    public void connectStage(Stage stage) {
        this.stage = stage;
        stage.setScene(new Scene(rootPane));
    }

    public Stage getStage() {
        return stage;
    }
}
