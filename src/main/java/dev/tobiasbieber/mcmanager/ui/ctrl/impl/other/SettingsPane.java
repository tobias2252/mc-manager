package dev.tobiasbieber.mcmanager.ui.ctrl.impl.other;

import dev.tobiasbieber.mcmanager.ui.ctrl.BaseController;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import javafx.scene.layout.BorderPane;

public class SettingsPane extends BaseController {

    public static class UI extends BaseUI<BorderPane, SettingsPane> {

        public UI() {
            super("/ui/fxml/other/settings-pane.fxml");
        }
    }
}
