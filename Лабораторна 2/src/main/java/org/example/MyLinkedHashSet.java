package org.example;

import java.util.LinkedHashMap;
import java.util.Set;

public class MyLinkedHashSet<E> {
    private final LinkedHashMap<E, Object> map = new LinkedHashMap<>();
    private static final Object DUMMY = new Object();

    public boolean add(E e) {
        return map.putIfAbsent(e, DUMMY) == null;
    }

    public boolean remove(E e) {
        return map.remove(e) != null;
    }

    public boolean contains(E e) {
        return map.containsKey(e);
    }

    public int size() {
        return map.size();
    }

    public Set<E> toSet() {
        return map.keySet();
    }
}
