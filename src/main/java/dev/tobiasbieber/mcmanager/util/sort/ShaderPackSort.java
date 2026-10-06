package dev.tobiasbieber.mcmanager.util.sort;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.domain.entity.impl.ShaderPack;

import java.util.Comparator;

public enum ShaderPackSort {

    NAME_ASC("sort.name.asc", Comparator.comparing(
            ShaderPack::getName,
            String.CASE_INSENSITIVE_ORDER
    )),
    NAME_DESC("sort.name.desc", Comparator.comparing(
            ShaderPack::getName,
            String.CASE_INSENSITIVE_ORDER
    ).reversed());

    private final String langKey;
    private final Comparator<ShaderPack> comparator;

    ShaderPackSort(String langKey, Comparator<ShaderPack> comparator) {
        this.langKey = langKey;
        this.comparator = comparator;
    }

    public Comparator<ShaderPack> getComparator() {
        return comparator;
    }

    @Override
    public String toString() {
        return AppContext.getInstance().getLangManager().get(langKey);
    }
}
