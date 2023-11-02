package net.burningtnt.earth2fix.utils;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public final class NullableMapEntry<K, V> implements Map.Entry<K, V> {
    @Nullable
    private final K key;

    @Nullable
    private final V value;

    public NullableMapEntry(@Nullable K key, @Nullable V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> Map.Entry<K, V> of(K key, V value) {
        return new NullableMapEntry<>(key, value);
    }

    @Override
    public @Nullable K getKey() {
        return this.key;
    }

    @Override
    public @Nullable V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V newValue) {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NullableMapEntry<?, ?> that = (NullableMapEntry<?, ?>) o;

        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
