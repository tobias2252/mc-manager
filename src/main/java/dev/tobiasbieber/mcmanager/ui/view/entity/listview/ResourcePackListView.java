package dev.tobiasbieber.mcmanager.ui.view.entity.listview;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.ui.view.entity.listcell.ResourcePackListCell;
import dev.tobiasbieber.mcmanager.ui.viewmodel.impl.ResourcePackViewModel;
import dev.tobiasbieber.mcmanager.util.FilterHelper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Comparator;

public class ResourcePackListView extends ListView<ResourcePackViewModel> {

    private FilteredList<ResourcePackViewModel> filteredList;
    private SortedList<ResourcePackViewModel> sortedList;

    public ResourcePackListView() {
        LangManager langManager = AppContext.getInstance().getLangManager();

        // create placeholder
        Label placeholder = new Label(langManager.get("resourcePackPane.listEmpty"));
        placeholder.getStyleClass().add("muted-text");
        setPlaceholder(placeholder);

        setCellFactory(cell -> new ResourcePackListCell());
    }

    public void setResourcePackList(ObservableList<ResourcePackViewModel> list) {
        filteredList = new FilteredList<>(list, pack -> true);
        sortedList = new SortedList<>(filteredList);
        setItems(sortedList);
    }

    public void filterList(String filter) {
        if (filteredList != null) {
            filteredList.setPredicate(pack -> {
                String text = pack.getFilterText();
                return FilterHelper.matchesFilter(filter, text);
            });
        }
    }

    public void sortList(Comparator<ResourcePackViewModel> comparator) {
        if (sortedList != null) {
            sortedList.setComparator(comparator);
        }
    }
}
