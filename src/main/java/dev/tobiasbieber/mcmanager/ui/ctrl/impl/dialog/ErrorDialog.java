package dev.tobiasbieber.mcmanager.ui.ctrl.impl.dialog;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.ui.SceneManager;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseController;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorDialog extends BaseController {

    // center
    @FXML
    public TextArea stackTraceArea;

    // bottom
    @FXML
    public Button okButton;

    @FXML
    private void initialize() {
        okButton.setOnAction(event -> ((Stage) okButton.getScene().getWindow()).close());
    }

    public static void show(Throwable e) {
        SceneManager sceneManager = AppContext.getInstance().getSceneManager();
        LangManager langManager = AppContext.getInstance().getLangManager();

        // create dialog
        UI ui = new UI();
        Stage dialog = new Stage();
        dialog.initOwner(sceneManager.getStage());
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(langManager.get("text.error"));

        // create scene
        dialog.setScene(new Scene(ui.getRoot()));
        dialog.setResizable(false);
        dialog.setWidth(600);
        dialog.setHeight(400);

        // set content
        ErrorDialog controller = ui.getController();
        StringBuilder stackTraceText = new StringBuilder();

        boolean first = true;
        for (StackTraceElement element : e.getStackTrace()) {
            if (first) {
                stackTraceText.append(element.toString());
                first = false;
            } else {
                stackTraceText.append("\n ...").append(element.toString());
            }
        }
        controller.stackTraceArea.setText(stackTraceText.toString());

        // show dialog
        dialog.showAndWait();
    }

    public static class UI extends BaseUI<BorderPane, ErrorDialog> {

        public UI() {
            super("/ui/fxml/dialog/error-dialog.fxml");
        }
    }
}
