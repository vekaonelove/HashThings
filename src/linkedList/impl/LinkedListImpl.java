package linkedList.impl;

import linkedList.Node;
import linkedList.LinkedList;

public class LinkedListImpl<T> implements LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T>  head) {
        this.head = head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void setTail(Node<T>  tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public Node<T> getElementByIndex(int elementIndex){
        if(elementIndex < 0 || elementIndex >= size){
            throw new IndexOutOfBoundsException("Index: " + elementIndex + ", Size: " + size);
        }
        Node<T> current = head;
        for(int i = 0; i < elementIndex; i++){
            current = current.getNext();
        }
        return current;
    }

    @Override
    public void addToEnd(T element){
        Node<T> newNode = new Node<>(element);
        if(head == null){
            head = newNode; //like the first and only element in our list
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }

    @Override
    public void addToHead(T element){
        Node<T> newNode = new Node<>(element);
        if(head == null){
            tail = newNode; //like the first and only element in our list
        } else {
            newNode.setNext(head);
        }
        head = newNode;
        size++;
    }

    @Override
    public void add (int index, T element){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if(index == 0){
            addToHead(element);
        } else if(index == size){
            addToEnd(element);
        } else {
            Node<T> newNode = new Node<>(element);
            Node<T> current = head;
            for(int i = 1; i < index; i++){
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            size++;
        }
    }

    @Override
    public void remove(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if(index == 0){
            head = head.getNext();
            if(head == null){
                tail = null;
            }
        } else {
            Node<T> current = head;
            for(int i = 1; i < index; i++){
                current = current.getNext();
            }
            if(current.getNext() == tail){
                tail = current;
            }
            current.setNext(current.getNext().getNext());
        }
        size--;
    }

    @Override
    public void revert(){
        Node<T> current = head;
        Node<T> prev = null;
        Node<T> next;
        while(current != null){
            next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }
        head = prev;
    }

    @Override
    public void printList(){
        Node<T> current = head;
        while(current != null){
            System.out.println(current.getData());
            current = current.getNext();
        }
    }
    @Override
    public String toString() {
        return "LinkedListImpl{" +
                "head=" + head +
                ", tail=" + tail +
                ", size=" + size +
                '}';
    }
}
