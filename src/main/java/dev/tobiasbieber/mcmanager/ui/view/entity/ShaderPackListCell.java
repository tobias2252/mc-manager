package dev.tobiasbieber.mcmanager.ui.view.entity;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.domain.entity.impl.ShaderPack;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class ShaderPackListCell extends ListCell<ShaderPack> {

    private final LangManager langManager;

    private final VBox container;
    private final Label nameLabel;
    private final Label pathLabel;

    public ShaderPackListCell() {
        langManager = AppContext.getInstance().getLangManager();

        // name label
        nameLabel = new Label();
        nameLabel.getStyleClass().add("bold-text");

        // version label
        pathLabel = new Label();
        pathLabel.getStyleClass().add("muted-text");

        // container
        container = new VBox();
        container.getChildren().addAll(nameLabel, pathLabel);
        container.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    protected void updateItem(ShaderPack item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setMouseTransparent(true);
            setGraphic(null);
            return;
        }

        nameLabel.setText(item.getName());
        String pathString = langManager.get("shaderPack.path") + ": " + item.getPath();
        pathLabel.setText(pathString);

        setMouseTransparent(false); // set false, or it will be transparent again
        setText(null);
        setGraphic(container);
    }
}
