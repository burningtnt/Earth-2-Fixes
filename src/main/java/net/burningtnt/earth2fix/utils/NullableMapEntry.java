package net.burningtnt.earth2fix.utils;

import java.util.Map;

public final class NullableMapEntry<K, V> implements Map.Entry<K, V> {
    public static <K, V> Map.Entry<K, V> of(K key, V value) {
        return new NullableMapEntry<>(key, value);
    }

    private final K key;

    private final V value;

    public NullableMapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V newValue) {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map.Entry))
            return false;
        Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
        return key.equals(e.getKey()) && value.equals(e.getValue());
    }

    @Override
    public int hashCode() {
        return key.hashCode() ^ value.hashCode();
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
