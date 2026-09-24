package dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity;

import dev.tobiasbieber.mcmanager.ui.ctrl.BaseController;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import javafx.scene.layout.BorderPane;

public class ResourcePackPane extends BaseController {

    public static class UI extends BaseUI<BorderPane, ResourcePackPane> {

        public UI() {
            super("/ui/fxml/entity/resource-pack-pane.fxml");
        }
    }
}
