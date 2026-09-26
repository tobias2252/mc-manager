package dev.tobiasbieber.mcmanager.ui.view.entity;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.domain.entity.impl.World;
import dev.tobiasbieber.mcmanager.util.Formatter;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class WorldListCell extends ListCell<World> {

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
        versionLabel.getStyleClass().addAll("muted-text");

        // last played label
        lastPlayedLabel = new Label();
        lastPlayedLabel.getStyleClass().addAll("muted-text");

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
    protected void updateItem(World item, boolean empty) {
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

        String lastPlayedString = langManager.get("world.lastPlayed") + ": "
                + Formatter.DEFAULT_DATE_TIME.format(item.getLastPlayed());
        lastPlayedLabel.setText(lastPlayedString);

        setMouseTransparent(false); // set false, or it will be transparent again
        setText(null);
        setGraphic(container);
    }
}
