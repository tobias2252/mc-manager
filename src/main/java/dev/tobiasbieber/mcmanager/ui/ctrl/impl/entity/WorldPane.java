package dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.domain.entity.impl.World;
import dev.tobiasbieber.mcmanager.service.entity.WorldService;
import dev.tobiasbieber.mcmanager.ui.TaskExecutor;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseController;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import dev.tobiasbieber.mcmanager.ui.view.entity.WorldListView;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class WorldPane extends BaseController {

    // services
    private WorldService worldService;

    // observable list for list view
    private final ObservableList<World> worldList = FXCollections.observableArrayList();

    @FXML
    public TextField searchField;
    @FXML
    public Button clearButton;
    @FXML
    public Button reloadButton;
    @FXML
    public Button detailsButton;
    @FXML
    public Button importButton;
    @FXML
    public Button exportButton;
    @FXML
    public Button deleteButton;
    @FXML
    public WorldListView worldListView;

    @FXML
    private void initialize() {
        worldService = AppContext.getInstance().getService(WorldService.class);

        // fill list with data
        loadWorldsAsync();
        worldListView.setWorldList(worldList); // connect list to list view

        createSearchField();
        createSelectionListener();
        disableDependentControls(true); // disable initially

        // clears selection and resets search field
        clearButton.setOnAction(event -> clearSearch());

        // reloads worlds from the installation directory
        reloadButton.setOnAction(event -> reloadWorlds());
    }

    @Override
    public void afterShow() {
        clearSearch();
    }

    // load worlds and store them in the observable list
    private void loadWorldsAsync() {
        Task<Void> worldLoadingTask = TaskExecutor.createTask(worldService::loadWorlds);
        worldLoadingTask.setOnSucceeded(event -> worldList.setAll(worldService.getWorlds()));
        TaskExecutor.runTask(worldLoadingTask, "worldLoadingTask");
    }

    // sets a filter on the world list view
    // updates 4 times per second
    private void createSearchField() {
        PauseTransition pause = new PauseTransition(Duration.millis(250));
        searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> worldListView.filterWorldList(newValue));
            pause.playFromStart();
        }));
    }

    private void createSelectionListener() {
        worldListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            disableDependentControls(newValue == null);
        });
    }

    // clear selection and search field
    private void clearSearch() {
        searchField.clear();
        worldListView.getSelectionModel().clearSelection();
    }

    // reloads worlds from the installation directory
    private void reloadWorlds() {
        clearSearch();
        loadWorldsAsync();
    }

    private void disableDependentControls(boolean disabled) {
        detailsButton.setDisable(disabled);
        deleteButton.setDisable(disabled);
    }

    public static class UI extends BaseUI<BorderPane, WorldPane> {

        public UI() {
            super("/ui/fxml/entity/world-pane.fxml");
        }
    }
}
