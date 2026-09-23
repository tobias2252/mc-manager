package dev.tobiasbieber.mcmanager.ui.ctrl;

import dev.tobiasbieber.mcmanager.core.AppContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ResourceBundle;

public abstract class BaseUI<T extends Parent, C extends BaseController> {

    private static final Logger log = LoggerFactory.getLogger(BaseUI.class);

    private final FXMLLoader loader;

    private T root;
    private C controller;

    public BaseUI(String fxml) {
        ResourceBundle bundle = AppContext.getInstance().getLangManager().getBundle();
        loader = new FXMLLoader(getClass().getResource(fxml), bundle);
    }

    public T getRoot() {
        if (root == null) load();
        return root;
    }

    public C getController() {
        if (controller == null) load();
        return controller;
    }

    private void load() {
        try {
            log.info("Loading FXML: {}", loader.getLocation());
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load FXML: " + loader.getLocation(), e);
        }
    }
}
