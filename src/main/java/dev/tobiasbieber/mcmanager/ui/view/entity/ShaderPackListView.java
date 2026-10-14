package dev.tobiasbieber.mcmanager.ui.view.entity;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.ui.model.impl.ShaderPackViewModel;
import dev.tobiasbieber.mcmanager.util.FilterHelper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Comparator;

public class ShaderPackListView extends ListView<ShaderPackViewModel> {

    private FilteredList<ShaderPackViewModel> filteredList;
    private SortedList<ShaderPackViewModel> sortedList;

    public ShaderPackListView() {
        LangManager langManager = AppContext.getInstance().getLangManager();

        // create placeholder
        Label placeholder = new Label(langManager.get("shaderPackPane.listEmpty"));
        placeholder.getStyleClass().add("muted-text");
        setPlaceholder(placeholder);

        setCellFactory(cell -> new ShaderPackListCell());
    }

    public void setShaderPackList(ObservableList<ShaderPackViewModel> list) {
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

    public void sortList(Comparator<ShaderPackViewModel> comparator) {
        if (sortedList != null) {
            sortedList.setComparator(comparator);
        }
    }
}
