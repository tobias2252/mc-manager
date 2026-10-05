package dev.tobiasbieber.mcmanager.ui.ctrl.impl.entity;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.domain.entity.impl.World;
import dev.tobiasbieber.mcmanager.service.entity.WorldService;
import dev.tobiasbieber.mcmanager.ui.TaskExecutor;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseController;
import dev.tobiasbieber.mcmanager.ui.ctrl.BaseUI;
import dev.tobiasbieber.mcmanager.ui.ctrl.impl.dialog.QuestionDialog;
import dev.tobiasbieber.mcmanager.ui.view.entity.WorldListView;
import dev.tobiasbieber.mcmanager.util.FileHelper;
import dev.tobiasbieber.mcmanager.util.sort.WorldSort;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

// TODO add drag and drop support for world list view
public class WorldPane extends BaseController {

    // managers
    private LangManager langManager;

    // services
    private WorldService worldService;

    // observable list for list view
    private final ObservableList<World> worldList = FXCollections.observableArrayList();

    // top
    @FXML
    public Button clearButton;
    @FXML
    public TextField searchField;
    @FXML
    public ComboBox<WorldSort> sortComboBox;

    // center-top
    @FXML
    public WorldListView worldListView;

    // center-bottom
    @FXML
    public Label pathLabel;
    @FXML
    public Label gameTypeLabel;
    @FXML
    public Label difficultyLabel;
    @FXML
    public Label hardcoreLabel;
    @FXML
    public Label allowCommandsLabel;
    @FXML
    public Label seedLabel;

    // bottom
    @FXML
    public Button openFolderButton;
    @FXML
    public Button backupButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button reloadButton;

    @FXML
    private void initialize() {
        langManager = AppContext.getInstance().getLangManager();
        worldService = AppContext.getInstance().getService(WorldService.class);

        // fill list with data
        loadWorldsAsync();
        worldListView.setWorldList(worldList); // connect list to list view

        createSearchField();
        createSortComboBox();
        sortComboBox.getSelectionModel().select(WorldSort.LAST_PLAYED_NEWEST); // default sorting

        createSelectionListener();
        disableDependentControls(true); // disable initially
        setWorldDetails(null);

        backupButton.setDisable(true); // TODO add backup support

        makeLabelCopyable(pathLabel);
        makeLabelCopyable(seedLabel);

        clearButton.setOnAction(event -> clearSearch());
        reloadButton.setOnAction(event -> reloadWorlds());
        openFolderButton.setOnAction(event -> openSelectedWorld());
        deleteButton.setOnAction(event -> deleteSelectedWorld());
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

    private void createSortComboBox() {
        sortComboBox.getItems().setAll(WorldSort.values());
        sortComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                worldListView.sortWorldList(newValue.getComparator());
        });
    }

    private void createSelectionListener() {
        worldListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            disableDependentControls(newValue == null);
            setWorldDetails(newValue);
        });
    }

    // clears selection and resets search field
    private void clearSearch() {
        searchField.clear();
        worldListView.getSelectionModel().clearSelection();
    }

    // reloads worlds from the installation directory
    private void reloadWorlds() {
        clearSearch();
        loadWorldsAsync();
    }

    // opens the selected world in the file explorer
    private void openSelectedWorld() {
        World world = worldListView.getSelectionModel().getSelectedItem();
        if (world != null) {
            FileHelper.openFolder(world.getPath());
        }
    }

    // confirms the deletion of the selected world and deletes it
    private void deleteSelectedWorld() {
        World world = worldListView.getSelectionModel().getSelectedItem();
        if (world != null) {
            String question = langManager.get("dialog.deleteQuestion");
            String description = langManager.get("dialog.deleteWarning", world.getName());
            boolean result = QuestionDialog.show(question, description, QuestionDialog.AnswerType.DELETE_CANCEL);

            if (result) {
                worldService.deleteWorld(world);
                worldList.remove(world); // remove from view
                worldListView.getSelectionModel().clearSelection();
            }
        }
    }

    // fills in the details of the selected world
    private void setWorldDetails(World world) {
        if (world != null) {
            pathLabel.setText(world.getPath().toString());

            switch (world.getGameType()) {
                case SURVIVAL -> gameTypeLabel.setText(langManager.get("gameType.survival"));
                case CREATIVE -> gameTypeLabel.setText(langManager.get("gameType.creative"));
                case ADVENTURE -> gameTypeLabel.setText(langManager.get("gameType.adventure"));
                case SPECTATOR -> gameTypeLabel.setText(langManager.get("gameType.spectator"));
            }

            switch (world.getDifficulty()) {
                case PEACEFUL -> difficultyLabel.setText(langManager.get("difficulty.peaceful"));
                case EASY -> difficultyLabel.setText(langManager.get("difficulty.easy"));
                case NORMAL -> difficultyLabel.setText(langManager.get("difficulty.normal"));
                case HARD -> difficultyLabel.setText(langManager.get("difficulty.hard"));
            }

            if (world.getHardcore()) {
                hardcoreLabel.setText(langManager.get("text.yes"));
            } else {
                hardcoreLabel.setText(langManager.get("text.no"));
            }

            if (world.getAllowCommands()) {
                allowCommandsLabel.setText(langManager.get("text.yes"));
            } else {
                allowCommandsLabel.setText(langManager.get("text.no"));
            }

            seedLabel.setText(world.getSeed().toString());

            // because click to copy action
            pathLabel.setMouseTransparent(false);
            seedLabel.setMouseTransparent(false);
        } else {
            pathLabel.setText("-");
            gameTypeLabel.setText("-");
            difficultyLabel.setText("-");
            hardcoreLabel.setText("-");
            allowCommandsLabel.setText("-");
            seedLabel.setText("-");

            // because click to copy action
            pathLabel.setMouseTransparent(true);
            seedLabel.setMouseTransparent(true);
        }
    }

    private void disableDependentControls(boolean disabled) {
        openFolderButton.setDisable(disabled);
        // backupButton.setDisable(disabled);
        deleteButton.setDisable(disabled);
    }

    private void makeLabelCopyable(Label label) {
        label.setOnMouseClicked(event -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();

            // copy to clipboard
            content.putString(label.getText());
            clipboard.setContent(content);
        });

        // set tooltip and style class
        Tooltip tooltip = new Tooltip(langManager.get("tooltip.copy"));
        label.setTooltip(tooltip);
        label.getStyleClass().add("copyable-label");
    }

    public static class UI extends BaseUI<BorderPane, WorldPane> {

        public UI() {
            super("/ui/fxml/entity/world-pane.fxml");
        }
    }
}
