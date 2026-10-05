package dev.tobiasbieber.mcmanager.util.sort;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.domain.entity.impl.World;

import java.util.Comparator;

public enum WorldSort {

    NAME_ASC("sort.name.asc", Comparator.comparing(
            World::getName,
            String.CASE_INSENSITIVE_ORDER
    )),
    NAME_DESC("sort.name.desc", Comparator.comparing(
            World::getName,
            String.CASE_INSENSITIVE_ORDER
    ).reversed()),
    VERSION_ASC("sort.version.asc", Comparator.comparing(
            World::getVersion,
            String.CASE_INSENSITIVE_ORDER
    )),
    VERSION_DESC("sort.version.desc", Comparator.comparing(
            World::getVersion,
            String.CASE_INSENSITIVE_ORDER
    ).reversed()),
    LAST_PLAYED_NEWEST("sort.lastPlayed.newest", Comparator.comparing(
            World::getLastPlayed,
            Comparator.nullsLast(Comparator.naturalOrder())
    ).reversed()),
    LAST_PLAYED_OLDEST("sort.lastPlayed.oldest", Comparator.comparing(
            World::getLastPlayed,
            Comparator.nullsLast(Comparator.naturalOrder())
    ));

    private final String langKey;
    private final Comparator<World> comparator;

    WorldSort(String langKey, Comparator<World> comparator) {
        this.langKey = langKey;
        this.comparator = comparator;
    }

    public Comparator<World> getComparator() {
        return comparator;
    }

    @Override
    public String toString() {
        return AppContext.getInstance().getLangManager().get(langKey);
    }
}
