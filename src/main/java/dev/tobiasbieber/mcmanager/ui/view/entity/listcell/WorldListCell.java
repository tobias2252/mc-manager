package dev.tobiasbieber.mcmanager.ui.view.entity.listcell;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.ui.viewmodel.impl.WorldViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class WorldListCell extends ListCell<WorldViewModel> {

    // managers
    private final LangManager langManager;

    private final HBox container;
    private final ImageView iconView;
    private final Label nameLabel;
    private final Label versionLabel;
    private final Label lastPlayedLabel;

    public WorldListCell() {
        langManager = AppContext.getInstance().getLangManager();

        // icon view
        iconView = new ImageView();
        iconView.setFitWidth(64);
        iconView.setFitHeight(64);
        iconView.setPreserveRatio(true);

        // name label
        nameLabel = new Label();
        nameLabel.getStyleClass().add("bold-text");

        // version label
        versionLabel = new Label();
        versionLabel.getStyleClass().add("muted-text");

        // last played label
        lastPlayedLabel = new Label();
        lastPlayedLabel.getStyleClass().add("muted-text");

        // text box
        VBox textBox = new VBox();
        textBox.getChildren().addAll(nameLabel, versionLabel, lastPlayedLabel);
        textBox.setAlignment(Pos.CENTER_LEFT);

        // container
        container = new HBox(10);
        container.getChildren().addAll(iconView, textBox);
        container.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    protected void updateItem(WorldViewModel item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setMouseTransparent(true);
            setGraphic(null);
            return;
        }

        iconView.setImage(item.getIcon());
        nameLabel.setText(item.getName());

        String versionString = langManager.get("world.version") + ": " + item.getVersion();
        versionLabel.setText(versionString);

        String lastPlayedString = langManager.get("world.lastPlayed") + ": " + item.getLastPlayed();
        lastPlayedLabel.setText(lastPlayedString);

        setMouseTransparent(false); // set false, or it will be transparent again
        setText(null);
        setGraphic(container);
    }
}
