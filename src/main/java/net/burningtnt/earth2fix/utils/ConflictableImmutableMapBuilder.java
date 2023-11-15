package net.burningtnt.earth2fix.utils;

import com.google.common.collect.ImmutableMap;
import net.burningtnt.earth2fix.Logging;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class ConflictableImmutableMapBuilder {
    private static final AtomicInteger FLAG = new AtomicInteger(0);
    private static final Field entriesField;
    private static final Field sizeField;

    static {
        try {
            entriesField = ImmutableMap.Builder.class.getDeclaredField("entries");
            entriesField.setAccessible(true);

            sizeField = ImmutableMap.Builder.class.getDeclaredField("size");
            sizeField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Cannot fix 'tschipp.carryon.client.event.RenderEvents.onRenderWorld'.", e);
        }
    }

    private ConflictableImmutableMapBuilder() {
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Map.Entry<K, V>[] getEntries(ImmutableMap.Builder<K, V> builder) {
        try {
            return (Map.Entry<K, V>[]) entriesField.get(builder);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("An Exception happend while getting field com.google.common.collect.ImmutableMap.Builder.entries", e);
        }
    }

    private static <K, V> int getSize(ImmutableMap.Builder<K, V> builder) {
        try {
            return sizeField.getInt(builder);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("An Exception happend while getting field com.google.common.collect.ImmutableMap.Builder.entries", e);
        }
    }

    public static <K, V> ImmutableMap<K, V> safeBuild(ImmutableMap.Builder<K, V> builder) {
        Map.Entry<K, V>[] entries = getEntries(builder);
        int size = getSize(builder);

        Map<K, V> result = new HashMap<>(size * 2); // Allocate enough spaces at first.
        for (int i = 0; i < size; i++) {
            Map.Entry<K, V> entry = entries[i];

            if (result.containsKey(entry.getKey())) {
                // Conflict detected
                V conflictValue = result.get(entry.getKey());

                if (!conflictValue.equals(entry.getValue())) {
                    // Oh no, it's a conflict.
                    boolean flag = FLAG.getAndIncrement() % 2 == 0;
                    Logging.getLogger().warn(
                            String.format(
                                    "Multiple entries in com.google.common.collect.ImmutableMap with the same key and different values are detected by Earth 2 Fixes.\n" +
                                            "  Key     : %s\n" +
                                            "  Value A : %s\n" +
                                            "  Value B : %s\n" +
                                            "  For avoiding encountering an IllegalStateException and crashing Minecraft, Entry %s is ignored. Your game may render incorrectly.",
                                    entry.getKey(), entry.getValue(), conflictValue, flag ? "A" : "B"
                            )
                    );

                    if (flag) {
                        result.put(entry.getKey(), entry.getValue());
                    }
                }
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return ImmutableMap.copyOf(result);
    }
}
