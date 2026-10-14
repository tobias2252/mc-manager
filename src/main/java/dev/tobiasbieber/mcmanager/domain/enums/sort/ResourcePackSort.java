package dev.tobiasbieber.mcmanager.domain.enums.sort;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.ui.viewmodel.impl.ResourcePackViewModel;

import java.util.Comparator;

public enum ResourcePackSort {

    NAME_ASC("sort.name.asc", Comparator.comparing(
            ResourcePackViewModel::getName,
            String.CASE_INSENSITIVE_ORDER
    )),
    NAME_DESC("sort.name.desc", Comparator.comparing(
            ResourcePackViewModel::getName,
            String.CASE_INSENSITIVE_ORDER
    ).reversed());

    private final String langKey;
    private final Comparator<ResourcePackViewModel> comparator;

    ResourcePackSort(String langKey, Comparator<ResourcePackViewModel> comparator) {
        this.langKey = langKey;
        this.comparator = comparator;
    }

    public Comparator<ResourcePackViewModel> getComparator() {
        return comparator;
    }

    @Override
    public String toString() {
        return AppContext.getInstance().getLangManager().get(langKey);
    }
}
