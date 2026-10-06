package dev.tobiasbieber.mcmanager.ui.ctrl.impl.other;

import dev.tobiasbieber.mcmanager.ui.ctrl.BaseController;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import javafx.scene.layout.BorderPane;

// TODO add option to change language
// TODO add option to change theme
// TODO add option to configure data time format
// TODO add option to change switch installation directory
public class SettingsPane extends BaseController {

    public static class UI extends BaseUI<BorderPane, SettingsPane> {

        public UI() {
            super("/ui/fxml/other/settings-pane.fxml");
        }
    }
}
