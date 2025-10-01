package org.example;

import java.util.ArrayList;
import java.util.List;

public class MyLinkedList<E> implements MyList<E> {
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E data) { this.data = data; }
    }

    private Node<E> head, tail;
    private int size = 0;

    @Override
    public void add(E e) {
        Node<E> node = new Node<>(e);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (index == size) { add(element); return; }
        Node<E> current = getNode(index);
        Node<E> node = new Node<>(element);
        node.next = current;
        node.prev = current.prev;
        if (current.prev != null) current.prev.next = node;
        else head = node;
        current.prev = node;
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
        return getNode(index).data;
    }

    private Node<E> getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<E> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current;
    }

    @Override
    public E remove(int index) {
        Node<E> node = getNode(index);
        if (node.prev != null) node.prev.next = node.next;
        else head = node.next;
        if (node.next != null) node.next.prev = node.prev;
        else tail = node.prev;
        size--;
        return node.data;
    }

    @Override
    public void set(int index, E element) {
        getNode(index).data = element;
    }

    @Override
    public int indexOf(E o) {
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if (current.data.equals(o)) return i;
            current = current.next;
        }
        return -1;
    }

    @Override
    public int size() { return size; }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            arr[i] = current.data;
            current = current.next;
        }
        return arr;
    }

    @Override
    public List<E> toList() {
        List<E> list = new ArrayList<>();
        Node<E> current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }
}
