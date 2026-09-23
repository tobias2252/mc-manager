package dev.tobiasbieber.mcmanager.ui;

import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
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

    public SceneManager() {
        rootPane = new BorderPane();
        mainPane = new StackPane();

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
