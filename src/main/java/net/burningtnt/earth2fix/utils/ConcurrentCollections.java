package net.burningtnt.earth2fix.utils;

import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public final class ConcurrentCollections {
    private ConcurrentCollections() {
    }

    public static <K, V> Map<K, V> ofNullableHashMap() {
        return ConcurrentNullableMapWrapper.of(new ConcurrentHashMap<>());
    }

    public static <K, V> SetMultimap<K, V> ofNullableHashSetMultiMap() {
        return Multimaps.newSetMultimap(ofNullableHashMap(), ConcurrentCollections::ofNullableHashSet);
    }

    public static <E> Set<E> ofNullableHashSet() {
        return Collections.newSetFromMap(ofNullableHashMap());
    }

    public static <E> Set<E> ofNullableArraySet() {
        return new CopyOnWriteArraySet<>();
    }

    public static <E> List<E> ofList() {
        return new CopyOnWriteArrayList<>();
    }


}
