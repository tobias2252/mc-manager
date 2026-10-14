package dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.domain.enums.sort.ShaderPackSort;
import dev.tobiasbieber.mcmanager.io.PathManager;
import dev.tobiasbieber.mcmanager.service.entity.ShaderPackService;
import dev.tobiasbieber.mcmanager.ui.TaskExecutor;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseController;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.dialog.QuestionDialog;
import dev.tobiasbieber.mcmanager.ui.viewmodel.impl.ShaderPackViewModel;
import dev.tobiasbieber.mcmanager.ui.view.entity.listview.ShaderPackListView;
import dev.tobiasbieber.mcmanager.util.FileHelper;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class ShaderPackPane extends BaseController {

    // managers
    private LangManager langManager;

    // services
    private ShaderPackService shaderPackService;

    // observable list for list view
    private final ObservableList<ShaderPackViewModel> shaderPackList = FXCollections.observableArrayList();

    // top
    @FXML
    public Button clearButton;
    @FXML
    public TextField searchField;
    @FXML
    public ComboBox<ShaderPackSort> sortComboBox;

    // center
    @FXML
    public ShaderPackListView shaderPackListView;

    // bottom
    @FXML
    public Button reloadButton;
    @FXML
    public Button openFolderButton;
    @FXML
    public Button deleteButton;

    @FXML
    private void initialize() {
        langManager = AppContext.getInstance().getLangManager();
        shaderPackService = AppContext.getInstance().getService(ShaderPackService.class);

        // fill list with data
        loadShaderPacksAsync();
        shaderPackListView.setShaderPackList(shaderPackList);

        createSearchField();
        createSortComboBox();
        sortComboBox.getSelectionModel().select(ShaderPackSort.NAME_ASC);

        createSelectionListener();
        disableDependentControls(true); // disable initially

        clearButton.setOnAction(event -> clearSearch());
        reloadButton.setOnAction(event -> reloadShaderPacks());
        openFolderButton.setOnAction(event -> openShaderPackFolder());
        deleteButton.setOnAction(event -> deleteSelectedShaderPack());
    }

    @Override
    public void afterShow() {
        clearSearch();
    }

    // load shader packs and store them in the observable list
    private void loadShaderPacksAsync() {
        Task<Void> task = TaskExecutor.createTask(shaderPackService::loadShaderPacks);
        task.setOnSucceeded(event -> {
            shaderPackList.clear();
            shaderPackService.getShaderPacks().forEach(pack -> shaderPackList.add(new ShaderPackViewModel(pack)));
        });
        TaskExecutor.runTask(task, "Shader pack loading task");
    }

    // sets a filter on the shader pack list view
    // updates 4 times per second
    private void createSearchField() {
        PauseTransition pause = new PauseTransition(Duration.millis(250));
        searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> shaderPackListView.filterList(newValue));
            pause.playFromStart();
        }));
    }

    private void createSortComboBox() {
        sortComboBox.getItems().setAll(ShaderPackSort.values());
        sortComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                shaderPackListView.sortList(newValue.getComparator());
        });
    }

    private void createSelectionListener() {
        shaderPackListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            disableDependentControls(newValue == null);
        });
    }

    // clears selection and resets search field
    private void clearSearch() {
        searchField.clear();
        shaderPackListView.getSelectionModel().clearSelection();
    }

    // reloads shader packs from the installation directory
    private void reloadShaderPacks() {
        clearSearch();
        loadShaderPacksAsync();
    }

    // opens the shader pack folder
    private void openShaderPackFolder() {
        PathManager pathManager = AppContext.getInstance().getPathManager();
        FileHelper.openFolder(pathManager.findShaderPackDir());
    }

    // confirms the deletion of the selected resource pack and deletes it
    private void deleteSelectedShaderPack() {
        ShaderPackViewModel pack = shaderPackListView.getSelectionModel().getSelectedItem();
        if (pack != null) {
            String question = langManager.get("dialog.deleteQuestion");
            String description = langManager.get("dialog.deleteWarning", pack.getName());
            boolean result = QuestionDialog.show(question, description, QuestionDialog.AnswerType.DELETE_CANCEL);

            if (result) {
                shaderPackService.deleteShaderPack(pack.getEntity());
                shaderPackList.remove(pack); // remove from view
                shaderPackListView.getSelectionModel().clearSelection();
            }
        }
    }

    private void disableDependentControls(boolean disabled) {
        deleteButton.setDisable(disabled);
    }

    public static class UI extends BaseUI<BorderPane, ShaderPackPane> {

        public UI() {
            super("/ui/fxml/entity/shader-pack-pane.fxml");
        }
    }
}
