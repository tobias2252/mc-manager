package dev.tobiasbieber.mcmanager.domain.enums.sort;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.ui.model.impl.WorldViewModel;

import java.util.Comparator;

public enum WorldSort {

    NAME_ASC("sort.name.asc", Comparator.comparing(
            WorldViewModel::getName,
            String.CASE_INSENSITIVE_ORDER
    )),
    NAME_DESC("sort.name.desc", Comparator.comparing(
            WorldViewModel::getName,
            String.CASE_INSENSITIVE_ORDER
    ).reversed()),
    VERSION_ASC("sort.version.asc", Comparator.comparing(
            WorldViewModel::getVersion,
            String.CASE_INSENSITIVE_ORDER
    )),
    VERSION_DESC("sort.version.desc", Comparator.comparing(
            WorldViewModel::getVersion,
            String.CASE_INSENSITIVE_ORDER
    ).reversed()),
    LAST_PLAYED_NEWEST("sort.lastPlayed.newest", Comparator.comparing(
            (WorldViewModel vm) -> vm.getEntity().getLastPlayed(),
            Comparator.nullsLast(Comparator.reverseOrder())
    )),
    LAST_PLAYED_OLDEST("sort.lastPlayed.oldest", Comparator.comparing(
            (WorldViewModel vm) -> vm.getEntity().getLastPlayed(),
            Comparator.nullsLast(Comparator.naturalOrder())
    ));

    private final String langKey;
    private final Comparator<WorldViewModel> comparator;

    WorldSort(String langKey, Comparator<WorldViewModel> comparator) {
        this.langKey = langKey;
        this.comparator = comparator;
    }

    public Comparator<WorldViewModel> getComparator() {
        return comparator;
    }

    @Override
    public String toString() {
        return AppContext.getInstance().getLangManager().get(langKey);
    }
}
