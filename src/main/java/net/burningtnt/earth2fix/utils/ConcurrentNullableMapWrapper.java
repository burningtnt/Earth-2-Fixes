package net.burningtnt.earth2fix.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@SuppressWarnings({"unchecked"})
public final class ConcurrentNullableMapWrapper<K, V> implements Map<K, V> {
    private static final Object NULL_VALUE_SPACE_HOLDER = new Object();
    private final Map<K, Object> delegate;
    private volatile Object nullValue = null;

    private ConcurrentNullableMapWrapper(Map<K, V> delegate) {
        this.delegate = (Map<K, Object>) delegate;
    }

    public static <K, V> Map<K, V> of(Map<K, V> delegate) {
        return new ConcurrentNullableMapWrapper<>(delegate);
    }

    @Override
    public int size() {
        return (this.nullValue == NULL_VALUE_SPACE_HOLDER ? 1 : 0) + this.delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return this.nullValue != NULL_VALUE_SPACE_HOLDER && this.delegate.isEmpty();
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        if (key == null) {
            return this.nullValue == NULL_VALUE_SPACE_HOLDER;
        } else {
            return this.delegate.containsKey(key);
        }
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        if (value == null) {
            return this.nullValue == NULL_VALUE_SPACE_HOLDER;
        } else {
            return this.delegate.containsValue(value);
        }
    }

    @Override
    public V get(@Nullable Object key) {
        if (key == null) {
            Object nv = this.nullValue;
            return nv == NULL_VALUE_SPACE_HOLDER ? null : (V) nv;
        } else {
            Object v = this.delegate.get(key);
            return v == NULL_VALUE_SPACE_HOLDER ? null : (V) v;
        }
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        if (key == null) {
            Object oldNV = this.nullValue;
            this.nullValue = value;
            return oldNV == NULL_VALUE_SPACE_HOLDER ? null : (V) oldNV;
        } else {
            Object oldV = this.delegate.put(key, value == null ? NULL_VALUE_SPACE_HOLDER : value);
            return oldV == NULL_VALUE_SPACE_HOLDER ? null : (V) oldV;
        }
    }

    @Override
    public V remove(Object key) {
        if (key == null) {
            Object oldNV = this.nullValue;
            this.nullValue = NULL_VALUE_SPACE_HOLDER;
            return oldNV == NULL_VALUE_SPACE_HOLDER ? null : (V) oldNV;
        } else {
            Object oldV = this.delegate.remove(key);
            return oldV == NULL_VALUE_SPACE_HOLDER ? null : (V) oldV;
        }
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        this.nullValue = null;
        this.delegate.clear();
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        Set<K> delegateKeySet = this.delegate.keySet();
        return new Set<>() {
            @Override
            public int size() {
                return ConcurrentNullableMapWrapper.this.size();
            }

            @Override
            public boolean isEmpty() {
                return ConcurrentNullableMapWrapper.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return ConcurrentNullableMapWrapper.this.containsKey(o);
            }

            @NotNull
            @Override
            public Iterator<K> iterator() {
                if (ConcurrentNullableMapWrapper.this.nullValue == null) {
                    return delegateKeySet.iterator();
                } else {
                    return Stream.concat(delegateKeySet.stream(), Stream.of((K) null)).iterator();
                }
            }

            @NotNull
            @Override
            public Object @NotNull [] toArray() {
                return delegateKeySet.toArray();
            }

            @NotNull
            @Override
            public <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {
                return delegateKeySet.toArray(a);
            }

            @Override
            public boolean add(K k) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean remove(Object o) {
                if (o == null) {
                    Object nv = ConcurrentNullableMapWrapper.this.nullValue;
                    ConcurrentNullableMapWrapper.this.nullValue = null;
                    return nv != null;
                } else {
                    return delegateKeySet.remove(o);
                }
            }

            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                for (Object item : c) {
                    if (!this.contains(item)) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public boolean addAll(@NotNull Collection<? extends K> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                boolean changed = false;
                for (K key : this) {
                    if (key == null) {
                        try {
                            if (!c.contains(null)) {
                                changed = true;
                                this.remove(null);
                            }
                        } catch (NullPointerException e) {
                            this.remove(null);
                            changed = true;
                        }
                    } else {
                        changed = true;
                        this.remove(key);
                    }
                }
                return changed;
            }

            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                boolean changed = false;
                for (Object item : c) {
                    if (this.remove(item)) {
                        changed = true;
                    }
                }
                return changed;
            }

            @Override
            public void clear() {
                ConcurrentNullableMapWrapper.this.clear();
            }
        };
    }

    @NotNull
    @Override
    public Collection<V> values() {
        Collection<Object> delegateValues = this.delegate.values();
        return new Collection<>() {
            @Override
            public int size() {
                return ConcurrentNullableMapWrapper.this.size();
            }

            @Override
            public boolean isEmpty() {
                return ConcurrentNullableMapWrapper.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return ConcurrentNullableMapWrapper.this.containsValue(o);
            }

            @NotNull
            @Override
            public Iterator<V> iterator() {
                Object nv = ConcurrentNullableMapWrapper.this.nullValue;
                if (nv == null) {
                    return delegateValues.stream().map(o -> o == NULL_VALUE_SPACE_HOLDER ? null : (V) o).iterator();
                } else {
                    return Stream.concat(
                            delegateValues.stream().map(o -> o == NULL_VALUE_SPACE_HOLDER ? null : (V) o),
                            nv == NULL_VALUE_SPACE_HOLDER ? Stream.of((V) null) : Stream.of((V) nv)
                    ).iterator();
                }
            }

            @NotNull
            @Override
            public Object @NotNull [] toArray() {
                return delegateValues.toArray();
            }

            @NotNull
            @Override
            public <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {
                return delegateValues.toArray(a);
            }

            @Override
            public boolean add(V v) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean remove(Object o) {
                if (o == null) {
                    if (ConcurrentNullableMapWrapper.this.nullValue != NULL_VALUE_SPACE_HOLDER) {
                        ConcurrentNullableMapWrapper.this.nullValue = null;
                        delegateValues.remove(NULL_VALUE_SPACE_HOLDER);
                        return true;
                    } else {
                        return delegateValues.remove(NULL_VALUE_SPACE_HOLDER);
                    }
                } else {
                    if (ConcurrentNullableMapWrapper.this.nullValue != o) {
                        ConcurrentNullableMapWrapper.this.nullValue = null;
                        delegateValues.remove(o);
                        return true;
                    } else {
                        return delegateValues.remove(o);
                    }
                }
            }

            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                for (Object item : c) {
                    if (!this.contains(item)) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public boolean addAll(@NotNull Collection<? extends V> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                boolean changed = false;
                for (Object item : c) {
                    if (this.remove(item)) {
                        changed = true;
                    }
                }
                return changed;
            }

            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                boolean changed = false;
                for (V value : this) {
                    if (value == null) {
                        try {
                            if (!c.contains(null)) {
                                changed = true;
                                this.remove(null);
                            }
                        } catch (NullPointerException e) {
                            this.remove(null);
                            changed = true;
                        }
                    } else {
                        changed = true;
                        this.remove(value);
                    }
                }
                return changed;
            }

            @Override
            public void clear() {
                ConcurrentNullableMapWrapper.this.clear();
            }
        };
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, Object>> delegateEntrySet = delegate.entrySet();
        return new Set<>() {
            @Override
            public int size() {
                return ConcurrentNullableMapWrapper.this.size();
            }

            @Override
            public boolean isEmpty() {
                return ConcurrentNullableMapWrapper.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return ConcurrentNullableMapWrapper.this.containsKey(o);
            }

            @NotNull
            @Override
            public Iterator<Entry<K, V>> iterator() {
                Object nv = ConcurrentNullableMapWrapper.this.nullValue;
                if (nv == null) {
                    return delegateEntrySet.stream().map(entry -> {
                        K key = entry.getKey();
                        Object value = entry.getValue();
                        return NullableMapEntry.of(
                                key == NULL_VALUE_SPACE_HOLDER ? null : key,
                                value == NULL_VALUE_SPACE_HOLDER ? null : (V) value
                        );
                    }).iterator();
                } else {
                    return Stream.concat(delegateEntrySet.stream().map(entry -> {
                        K key = entry.getKey();
                        Object value = entry.getValue();
                        return NullableMapEntry.of(
                                key == NULL_VALUE_SPACE_HOLDER ? null : key,
                                value == NULL_VALUE_SPACE_HOLDER ? null : (V) value
                        );
                    }), Stream.of(NullableMapEntry.of((K) null, nv == NULL_VALUE_SPACE_HOLDER ? null : (V) nv))).iterator();
                }
            }

            @NotNull
            @Override
            public Object @NotNull [] toArray() {
                return delegateEntrySet.toArray();
            }

            @NotNull
            @Override
            public <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {
                return delegateEntrySet.toArray(a);
            }

            @Override
            public boolean add(Entry<K, V> kvEntry) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean remove(Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                Object key = ((Entry<?, ?>) o).getKey();
                Object value = ((Entry<?, ?>) o).getValue();

                if (key != null && value != null) {
                    return delegateEntrySet.remove(o);
                }

                if (key == null) {
                    Object nv = ConcurrentNullableMapWrapper.this.nullValue;
                    if (value == (nv == NULL_VALUE_SPACE_HOLDER ? null : nv)) {
                        ConcurrentNullableMapWrapper.this.nullValue = null;
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return delegateEntrySet.remove(NullableMapEntry.of(key, NULL_VALUE_SPACE_HOLDER));
                }
            }

            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                for (Object item : c) {
                    if (!this.contains(item)) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public boolean addAll(@NotNull Collection<? extends Entry<K, V>> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                boolean changed = false;
                for (Map.Entry<K, V> entry : this) {
                    if (!c.contains(entry)) {
                        this.remove(entry);
                        changed = true;
                    }
                }
                return changed;
            }

            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                boolean changed = false;
                for (Object item : c) {
                    if (this.remove(item)) {
                        changed = true;
                    }
                }
                return changed;
            }

            @Override
            public void clear() {
                ConcurrentNullableMapWrapper.this.clear();
            }
        };
    }
}
