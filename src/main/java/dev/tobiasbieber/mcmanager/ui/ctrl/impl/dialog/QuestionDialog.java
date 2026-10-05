package dev.tobiasbieber.mcmanager.ui.ctrl.impl.dialog;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.ui.SceneManager;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseController;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class QuestionDialog extends BaseController {

    @FXML
    public Label questionLabel;
    @FXML
    public Label descriptionLabel;
    @FXML
    public Button positiveButton;
    @FXML
    public Button negativeButton;

    public AtomicBoolean result;

    public enum AnswerType {
        CONFIRM_CANCEL,
        DELETE_CANCEL
    }

    @FXML
    private void initialize() {
        result = new AtomicBoolean(false);

        positiveButton.setOnAction(event -> {
            result.set(true);
            ((Stage) positiveButton.getScene().getWindow()).close();
        });
        negativeButton.setOnAction(event -> {
            ((Stage) negativeButton.getScene().getWindow()).close();
        });
    }

    public static boolean show(String question, String description, AnswerType answerType) {
        SceneManager sceneManager = AppContext.getInstance().getSceneManager();
        LangManager langManager = AppContext.getInstance().getLangManager();

        // create dialog
        UI ui = new UI();
        Stage dialog = new Stage();
        dialog.initOwner(sceneManager.getStage());
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(langManager.get("text.question"));

        // create scene
        dialog.setScene(new Scene(ui.getRoot()));
        dialog.setResizable(false);
        dialog.setWidth(500);
        dialog.setHeight(210);

        // set content
        QuestionDialog controller = ui.getController();
        controller.questionLabel.setText(question);
        controller.descriptionLabel.setText(description);

        switch (answerType) {
            case CONFIRM_CANCEL -> {
                controller.positiveButton.setText(langManager.get("text.confirm"));
                controller.negativeButton.setText(langManager.get("text.cancel"));
            }
            case DELETE_CANCEL -> {
                controller.positiveButton.setText(langManager.get("text.delete"));
                controller.positiveButton.getStyleClass().add("destructive-button");
                controller.negativeButton.setText(langManager.get("text.cancel"));
            }
        }

        dialog.showAndWait();
        return controller.result.get();
    }

    public static class UI extends BaseUI<BorderPane, QuestionDialog> {

        public UI() {
            super("/ui/fxml/dialog/question-dialog.fxml");
        }
    }
}
