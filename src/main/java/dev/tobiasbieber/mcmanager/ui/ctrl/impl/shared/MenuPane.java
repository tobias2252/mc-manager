package dev.tobiasbieber.mcmanager.ui.ctrl.impl.shared;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.ui.SceneManager;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseController;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity.ModPane;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity.ResourcePackPane;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity.ShaderPackPane;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity.WorldPane;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.other.SettingsPane;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class MenuPane extends BaseController {

    @FXML
    public ToggleButton worldButton;
    @FXML
    public ToggleButton modButton;
    @FXML
    public ToggleButton resourcePackButton;
    @FXML
    public ToggleButton shaderPackButton;
    @FXML
    public ToggleButton settingsButton;

    @FXML
    private void initialize() {
        ToggleGroup toggleGroup = new ToggleGroup();

        worldButton.setToggleGroup(toggleGroup);
        modButton.setToggleGroup(toggleGroup);
        resourcePackButton.setToggleGroup(toggleGroup);
        shaderPackButton.setToggleGroup(toggleGroup);
        settingsButton.setToggleGroup(toggleGroup);

        // world button is selected first
        // has to be before listener creation
        toggleGroup.selectToggle(worldButton);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                // prevents button deselection
                if (oldValue != null) {
                    toggleGroup.selectToggle(oldValue);
                }
                return;
            }
            handleSelection((ToggleButton) newValue);
        });
    }

    private void handleSelection(ToggleButton button) {
        SceneManager sceneManager = AppContext.getInstance().getSceneManager();
        if (button == worldButton) {
            sceneManager.showUI(sceneManager.worldPaneUI, WorldPane.class);
        } else if (button == modButton) {
            sceneManager.showUI(sceneManager.modPaneUI, ModPane.class);
        } else if (button == resourcePackButton) {
            sceneManager.showUI(sceneManager.resourcePackPaneUI, ResourcePackPane.class);
        } else if (button == shaderPackButton) {
            sceneManager.showUI(sceneManager.shaderPackPaneUI, ShaderPackPane.class);
        } else if (button == settingsButton) {
            sceneManager.showUI(sceneManager.settingsPaneUI, SettingsPane.class);
        }
    }

    public static class UI extends BaseUI<VBox, MenuPane> {

        public UI() {
            super("/ui/fxml/shared/menu-pane.fxml");
        }
    }
}
