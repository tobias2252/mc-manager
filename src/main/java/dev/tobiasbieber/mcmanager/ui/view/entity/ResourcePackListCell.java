package dev.tobiasbieber.mcmanager.ui.view.entity;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.domain.entity.impl.ResourcePack;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ResourcePackListCell extends ListCell<ResourcePack> {

    private final LangManager langManager;

    private final HBox container;
    private final ImageView iconView;
    private final Label nameLabel;
    private final Label descriptionLabel;
    private final Label pathLabel;

    public ResourcePackListCell() {
        langManager = AppContext.getInstance().getLangManager();

        // icon view
        iconView = new ImageView();
        iconView.setFitWidth(64);
        iconView.setFitHeight(64);
        iconView.setPreserveRatio(true);

        // name label
        nameLabel = new Label();
        nameLabel.getStyleClass().add("bold-text");

        // description label
        descriptionLabel = new Label();

        // path label
        pathLabel = new Label();
        pathLabel.getStyleClass().add("muted-text");

        // text box
        VBox textBox = new VBox();
        textBox.getChildren().addAll(nameLabel, descriptionLabel, pathLabel);
        textBox.setAlignment(Pos.CENTER_LEFT);

        // container
        container = new HBox(10);
        container.getChildren().addAll(iconView, textBox);
        container.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    protected void updateItem(ResourcePack item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setMouseTransparent(true);
            setGraphic(null);
            return;
        }

        iconView.setImage(item.getIcon());
        nameLabel.setText(item.getName());
        descriptionLabel.setText(item.getDescription());

        String pathString = langManager.get("resourcePack.path") + ": " + item.getPath();
        pathLabel.setText(pathString);

        setMouseTransparent(false); // set false, or it will be transparent again
        setText(null);
        setGraphic(container);
    }
}
