package dev.tobiasbieber.mcmanager.util.sort;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.domain.entity.impl.ResourcePack;

import java.util.Comparator;

public enum ResourcePackSort {

    NAME_ASC("sort.name.asc", Comparator.comparing(
            ResourcePack::getName,
            String.CASE_INSENSITIVE_ORDER
    )),
    NAME_DESC("sort.name.desc", Comparator.comparing(
            ResourcePack::getName,
            String.CASE_INSENSITIVE_ORDER
    ).reversed());

    private final String langKey;
    private final Comparator<ResourcePack> comparator;

    ResourcePackSort(String langKey, Comparator<ResourcePack> comparator) {
        this.langKey = langKey;
        this.comparator = comparator;
    }

    public Comparator<ResourcePack> getComparator() {
        return comparator;
    }

    @Override
    public String toString() {
        return AppContext.getInstance().getLangManager().get(langKey);
    }
}
