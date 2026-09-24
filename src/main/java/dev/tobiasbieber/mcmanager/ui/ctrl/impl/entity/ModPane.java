package dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity;

import dev.tobiasbieber.mcmanager.ui.ctrl.BaseController;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import javafx.scene.layout.BorderPane;

public class ModPane extends BaseController {

    public static class UI extends BaseUI<BorderPane, ModPane> {

        public UI() {
            super("/ui/fxml/entity/mod-pane.fxml");
        }
    }
}
