package dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.domain.entity.impl.ResourcePack;
import dev.tobiasbieber.mcmanager.domain.entity.impl.World;
import dev.tobiasbieber.mcmanager.io.PathManager;
import dev.tobiasbieber.mcmanager.service.entity.ResourcePackService;
import dev.tobiasbieber.mcmanager.ui.TaskExecutor;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseController;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.dialog.QuestionDialog;
import dev.tobiasbieber.mcmanager.ui.view.entity.ResourcePackListView;
import dev.tobiasbieber.mcmanager.util.FileHelper;
import dev.tobiasbieber.mcmanager.util.sort.ResourcePackSort;
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

public class ResourcePackPane extends BaseController {

    // managers
    private LangManager langManager;

    // services
    private ResourcePackService resourcePackService;

    // observable list for list view
    private final ObservableList<ResourcePack> resourcePackList = FXCollections.observableArrayList();

    // top
    @FXML
    public Button clearButton;
    @FXML
    public TextField searchField;
    @FXML
    public ComboBox<ResourcePackSort> sortComboBox;

    // center
    @FXML
    public ResourcePackListView resourcePackListView;

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
        resourcePackService = AppContext.getInstance().getService(ResourcePackService.class);

        // fill list with data
        loadResourcePacksAsync();
        resourcePackListView.setResourcePackList(resourcePackList);

        createSearchField();
        createSortComboBox();
        sortComboBox.getSelectionModel().select(ResourcePackSort.NAME_ASC);

        createSelectionListener();
        disableDependentControls(true); // disable initially

        clearButton.setOnAction(event -> clearSearch());
        reloadButton.setOnAction(event -> reloadResourcePacks());
        openFolderButton.setOnAction(event -> openResourcePackFolder());
        deleteButton.setOnAction(event -> deleteSelectedResourcePack());
    }

    @Override
    public void afterShow() {
        clearSearch();
    }

    // load resource packs and store them in the observable list
    private void loadResourcePacksAsync() {
        Task<Void> resourcePackLoadingTask = TaskExecutor.createTask(resourcePackService::loadResourcePacks);
        resourcePackLoadingTask.setOnSucceeded(event -> resourcePackList.setAll(resourcePackService.getResourcePacks()));
        TaskExecutor.runTask(resourcePackLoadingTask, "resourcePackLoadingTask");
    }

    // sets a filter on the resource pack list view
    // updates 4 times per second
    private void createSearchField() {
        PauseTransition pause = new PauseTransition(Duration.millis(250));
        searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> resourcePackListView.filterResourcePackList(newValue));
            pause.playFromStart();
        }));
    }

    private void createSortComboBox() {
        sortComboBox.getItems().setAll(ResourcePackSort.values());
        sortComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                resourcePackListView.sortResourcePackList(newValue.getComparator());
        });
    }

    private void createSelectionListener() {
        resourcePackListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            disableDependentControls(newValue == null);
        });
    }

    // clears selection and resets search field
    private void clearSearch() {
        searchField.clear();
        resourcePackListView.getSelectionModel().clearSelection();
    }

    // reloads resource packs from the installation directory
    private void reloadResourcePacks() {
        clearSearch();
        loadResourcePacksAsync();
    }

    // opens the resource pack folder
    private void openResourcePackFolder() {
        PathManager pathManager = AppContext.getInstance().getPathManager();
        FileHelper.openFolder(pathManager.findResourcePackDir());
    }

    // confirms the deletion of the selected resource pack and deletes it
    private void deleteSelectedResourcePack() {
        ResourcePack resourcePack = resourcePackListView.getSelectionModel().getSelectedItem();
        if (resourcePack != null) {
            String question = langManager.get("dialog.deleteQuestion");
            String description = langManager.get("dialog.deleteWarning", resourcePack.getName());
            boolean result = QuestionDialog.show(question, description, QuestionDialog.AnswerType.DELETE_CANCEL);

            if (result) {
                resourcePackService.deleteResourcePack(resourcePack);
                resourcePackList.remove(resourcePack); // remove from view
                resourcePackListView.getSelectionModel().clearSelection();
            }
        }
    }

    private void disableDependentControls(boolean disabled) {
        deleteButton.setDisable(disabled);
    }

    public static class UI extends BaseUI<BorderPane, ResourcePackPane> {

        public UI() {
            super("/ui/fxml/entity/resource-pack-pane.fxml");
        }
    }
}
