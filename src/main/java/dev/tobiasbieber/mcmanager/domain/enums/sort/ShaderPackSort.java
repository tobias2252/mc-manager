package dev.tobiasbieber.mcmanager.domain.enums.sort;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.ui.viewmodel.impl.ShaderPackViewModel;

import java.util.Comparator;

public enum ShaderPackSort {

    NAME_ASC("sort.name.asc", Comparator.comparing(
            ShaderPackViewModel::getName,
            String.CASE_INSENSITIVE_ORDER
    )),
    NAME_DESC("sort.name.desc", Comparator.comparing(
            ShaderPackViewModel::getName,
            String.CASE_INSENSITIVE_ORDER
    ).reversed());

    private final String langKey;
    private final Comparator<ShaderPackViewModel> comparator;

    ShaderPackSort(String langKey, Comparator<ShaderPackViewModel> comparator) {
        this.langKey = langKey;
        this.comparator = comparator;
    }

    public Comparator<ShaderPackViewModel> getComparator() {
        return comparator;
    }

    @Override
    public String toString() {
        return AppContext.getInstance().getLangManager().get(langKey);
    }
}
