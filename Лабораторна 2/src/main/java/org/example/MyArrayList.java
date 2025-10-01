package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class MyArrayList<E> implements MyList<E>, RandomAccess {
    private Object[] data;
    private int size;

    public MyArrayList() {
        data = new Object[10];
        size = 0;
    }

    @Override
    public void add(E e) {
        ensureCapacity();
        data[size++] = e;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        ensureCapacity();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    public void addAll(E[] c) {
        for (E e : c) add(e);
    }

    @Override
    public void addAll(int index, E[] c) {
        for (int i = 0; i < c.length; i++) {
            add(index + i, c[i]);
        }
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (E) data[index];
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        E removed = (E) data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
        return removed;
    }

    @Override
    public void set(int index, E element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        data[index] = element;
    }

    @Override
    public int indexOf(E o) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) return i;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        System.arraycopy(data, 0, arr, 0, size);
        return arr;
    }

    @Override
    public List<E> toList() {
        List<E> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add((E) data[i]);
        }
        return list;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
    }
}
