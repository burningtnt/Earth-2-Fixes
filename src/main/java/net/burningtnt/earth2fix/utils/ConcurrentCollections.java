package net.burningtnt.earth2fix.utils;

import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ConcurrentCollections {
    private ConcurrentCollections() {
    }

    public static <K, V> Map<K, V> ofNullableMap() {
        return ConcurrentNullableMapWrapper.of(new ConcurrentHashMap<>());
    }

    public static <K, V> Set<K> ofNullableSet() {
        return Collections.newSetFromMap(ofNullableMap());
    }

    public static <E> List<E> ofList() {
        return new CopyOnWriteArrayList<>();
    }

    public static <K, V> SetMultimap<K, V> ofNullableHashMultiMap() {
        return Multimaps.newSetMultimap(ofNullableMap(), ConcurrentCollections::ofNullableSet);
    }
}
