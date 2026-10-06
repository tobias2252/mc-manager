package dev.tobiasbieber.mcmanager.ui.view.entity;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.core.LangManager;
import dev.tobiasbieber.mcmanager.domain.entity.impl.ShaderPack;
import dev.tobiasbieber.mcmanager.util.FilterHelper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Comparator;

public class ShaderPackListView extends ListView<ShaderPack> {

    private FilteredList<ShaderPack> filteredList;
    private SortedList<ShaderPack> sortedList;

    public ShaderPackListView() {
        LangManager langManager = AppContext.getInstance().getLangManager();

        // create placeholder
        Label placeholder = new Label(langManager.get("shaderPackPane.listEmpty"));
        placeholder.getStyleClass().add("muted-text");
        setPlaceholder(placeholder);

        setCellFactory(cell -> new ShaderPackListCell());
    }

    public void setShaderPackList(ObservableList<ShaderPack> shaderPackList) {
        filteredList = new FilteredList<>(shaderPackList, pack -> true);
        sortedList = new SortedList<>(filteredList);
        setItems(sortedList);
    }

    public void filterShaderPackList(String filter) {
        if (filteredList != null) {
            filteredList.setPredicate(pack -> {
                String text = pack.getFilterText();
                return FilterHelper.matchesFilter(filter, text);
            });
        }
    }

    public void sortShaderPackList(Comparator<ShaderPack> comparator) {
        if (sortedList != null) {
            sortedList.setComparator(comparator);
        }
    }
}
