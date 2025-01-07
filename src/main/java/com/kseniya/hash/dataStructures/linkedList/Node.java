package com.kseniya.hash.dataStructures.linkedList;

import java.util.Objects;

public class Node<T> {
    private T data;
    private Node<T> next;
    private Node<T>  prev;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T>  getNext() {
        return next;
    }

    public void setNext(Node<T>  next) {
        this.next = next;
    }

    public Node<T>  getPrev() {
        return prev;
    }

    public void setPrev(Node<T>  prev) {
        this.prev = prev;
    }

    public Node(T data) {
        this.data = data;
        prev = next = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(data, node.data) && Objects.equals(next, node.next) && Objects.equals(prev, node.prev);
    }

}
