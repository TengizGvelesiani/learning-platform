package com.tengo.learningplatform.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class SimpleLinkedList<E> implements Iterable<E> {

    private Node<E> head;
    private int size;

    private static final class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }

    public void add(E element) {
        if (head == null) {
            head = new Node<>(element);
        } else {
            Node<E> cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = new Node<>(element);
        }
        size++;
    }

    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.data;
    }

    public boolean remove(E element) {
        if (head == null) {
            return false;
        }
        if (element == null ? head.data == null : element.equals(head.data)) {
            head = head.next;
            size--;
            return true;
        }
        Node<E> cur = head;
        while (cur.next != null) {
            if (element == null ? cur.next.data == null : element.equals(cur.next.data)) {
                cur.next = cur.next.next;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> next = head;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public E next() {
                if (next == null) {
                    throw new NoSuchElementException();
                }
                E val = next.data;
                next = next.next;
                return val;
            }
        };
    }
}




