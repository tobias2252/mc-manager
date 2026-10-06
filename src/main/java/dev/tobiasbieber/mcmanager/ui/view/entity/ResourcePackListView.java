package dev.tobiasbieber.mcmanager.ui.view.entity;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.domain.entity.impl.ResourcePack;
import dev.tobiasbieber.mcmanager.util.FilterHelper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Comparator;

public class ResourcePackListView extends ListView<ResourcePack> {

    private FilteredList<ResourcePack> filteredList;
    private SortedList<ResourcePack> sortedList;

    public ResourcePackListView() {
        LangManager langManager = AppContext.getInstance().getLangManager();

        // create placeholder
        Label placeholder = new Label(langManager.get("resourcePackPane.listEmpty"));
        placeholder.getStyleClass().add("muted-text");
        setPlaceholder(placeholder);

        setCellFactory(cell -> new ResourcePackListCell());
    }

    public void setResourcePackList(ObservableList<ResourcePack> resourcePackList) {
        filteredList = new FilteredList<>(resourcePackList, pack -> true);
        sortedList = new SortedList<>(filteredList);
        setItems(sortedList);
    }

    public void filterResourcePackList(String filter) {
        if (filteredList != null) {
            filteredList.setPredicate(pack -> {
                String text = pack.getFilterText();
                return FilterHelper.matchesFilter(filter, text);
            });
        }
    }

    public void sortResourcePackList(Comparator<ResourcePack> comparator) {
        if (sortedList != null) {
            sortedList.setComparator(comparator);
        }
    }
}
