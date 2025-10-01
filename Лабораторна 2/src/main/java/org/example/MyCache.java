package org.example;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyCache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;

    public MyCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > MyCache.this.capacity;
            }
        };
    }

    public void put(K key, V value) {
        if (key == null || value == null) throw new NullPointerException();
        cache.put(key, value);
    }

    public V get(K key) {
        return cache.get(key);
    }

    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    public int size() {
        return cache.size();
    }

    public void clear() {
        cache.clear();
    }
}
