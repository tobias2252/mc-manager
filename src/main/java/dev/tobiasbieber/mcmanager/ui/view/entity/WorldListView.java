package dev.tobiasbieber.mcmanager.ui.view.entity;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.ui.model.impl.WorldViewModel;
import dev.tobiasbieber.mcmanager.util.FilterHelper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Comparator;

public class WorldListView extends ListView<WorldViewModel> {

    private FilteredList<WorldViewModel> filteredList;
    private SortedList<WorldViewModel> sortedList;

    public WorldListView() {
        LangManager langManager = AppContext.getInstance().getLangManager();

        // create placeholder
        Label placeholder = new Label(langManager.get("worldPane.listEmpty"));
        placeholder.getStyleClass().add("muted-text");
        setPlaceholder(placeholder);

        setCellFactory(cell -> new WorldListCell());
    }

    public void setWorldList(ObservableList<WorldViewModel> list) {
        filteredList = new FilteredList<>(list, world -> true);
        sortedList = new SortedList<>(filteredList);
        setItems(sortedList);
    }

    public void filterList(String filter) {
        if (filteredList != null) {
            filteredList.setPredicate(world -> {
                String text = world.getFilterText();
                return FilterHelper.matchesFilter(filter, text);
            });
        }
    }

    public void sortList(Comparator<WorldViewModel> comparator) {
        if (sortedList != null) {
            sortedList.setComparator(comparator);
        }
    }
}
