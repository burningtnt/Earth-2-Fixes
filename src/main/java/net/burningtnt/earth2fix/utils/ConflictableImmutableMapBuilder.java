package net.burningtnt.earth2fix.utils;

import com.google.common.collect.ImmutableMap;
import net.burningtnt.earth2fix.Earth2Fixes;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class ConflictableImmutableMapBuilder {
    private static final Random RANDOM = new Random();

    private ConflictableImmutableMapBuilder() {
    }

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
        Map<K, V> result = new HashMap<>();

        Map.Entry<K, V>[] entries = getEntries(builder);
        for (int i = 0; i < getSize(builder); i++) {
            Map.Entry<K, V> entry = entries[i];
            if (result.containsKey(entry.getKey())) {
                // Conflict detected
                V conflictValue = result.get(entry.getKey());

                if (!conflictValue.equals(entry.getValue())) {
                    // Oh no, it's a conflict.
                    boolean flag = RANDOM.nextBoolean();
                    Earth2Fixes.getLogger().warn(
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
