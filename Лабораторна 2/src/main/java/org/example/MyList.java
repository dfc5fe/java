package org.example;

import java.util.List;

public interface MyList<E> {
    void add(E e);
    void add(int index, E element);
    void addAll(E[] c);
    void addAll(int index, E[] c);
    E get(int index);
    E remove(int index);
    void set(int index, E element);
    int indexOf(E o);
    int size();
    Object[] toArray();
    List<E> toList(); // для зручності інтеграції з існуючим кодом
}
