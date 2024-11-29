package hashTable.impl;
import hashTable.Entry;
import hashTable.HashTable;
import linkedList.Node;
import linkedList.impl.LinkedListImpl;

import java.util.Arrays;
import java.util.Optional;

public class HashMapImpl<K extends Comparable<K>, V> implements HashTable<K, V> {
    private LinkedListImpl<Entry<K, V>>[] buckets;
    private int size;
    private int capacity = 8; //default capacity - amount of buckets

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public HashMapImpl(LinkedListImpl<Entry<K, V>>[] buckets) {
        this.buckets = buckets;
    }

    @Override
    public LinkedListImpl<Object> getBuckets() {
        LinkedListImpl<Object> bucketsList = new LinkedListImpl<>();
        for (LinkedListImpl<Entry<K, V>> bucket : buckets) {
            bucketsList.addToEnd(bucket);
        }
        return bucketsList;
    }

    @Override
    public void add(K key, V value) {
        int hash = key.hashCode();
        int index = hash % capacity;
        if (buckets[index] == null) {
            buckets[index] = new LinkedListImpl<>();
        }
        Entry<K, V> entry = new Entry<>(key, value);
        buckets[index].addToEnd(entry);
        size++;
    }

    @Override
    public V getElement(K key) {
        for (int i = 0; i < capacity; i++) {
            if (buckets[i] != null && buckets[i].getHead() != null) {
                Node<Entry<K, V>> current = buckets[i].getHead();
                while (current != null) {
                    Entry<K, V> entry = current.getData();
                    if (entry.getKey().equals(key)) {
                        return entry.getValue();
                    }
                    current = current.getNext();
                }
            }
        }
        return null;
    }

    @Override
    public Optional<?> getOrDefault(K key, V value) {
        for (int i = 0; i < capacity; i++) {
            if (buckets[i] != null && buckets[i].getHead() != null) {
                Node<Entry<K, V>> current = buckets[i].getHead();
                while (current != null) {
                    Entry<K, V> entry = current.getData();
                    if (entry.getKey().equals(key)) {
                        return Optional.of(entry.getValue());
                    }
                    current = current.getNext();
                }
            }
        }
        return Optional.of(value);
    }

    @Override
    public void replace(K key, V value1, V value2) {
        for (int i = 0; i < capacity; i++) {
            if (buckets[i] != null && buckets[i].getHead() != null) {
                Node<Entry<K, V>> current = buckets[i].getHead();
                while (current != null) {
                    Entry<K, V> entry = current.getData();
                    if (entry.getKey().equals(key) && entry.getValue().equals(value1)) {
                        entry.setValue(value2);
                        return;
                    }
                    current = current.getNext();
                }
            }
        }
    }
    @Override
    public V pop(K key) {
        int hash = key.hashCode();
        int index = hash % capacity;
        Node<Entry<K, V>> return_node = buckets[index].getHead();
        if (buckets[index] != null) {
            Node<Entry<K, V>> current = buckets[index].getHead();
            Node<Entry<K, V>> prev = null;
            while (current != null) {
                Entry<K, V> entry = current.getData();
                if (entry.getKey().equals(key)) {
                    if (prev == null) {
                        buckets[index].setHead(current.getNext());
                    } else {
                        prev.setNext(current.getNext());
                    }
                    size--;
                    return entry.getValue();
                }
                prev = current;
                current = current.getNext();
            }
        }
        return return_node.getData().getValue();
    }

    @Override
    public void printHashTable() {
        if (size > 0) {
            System.out.println("HashMap:");
        } else {
            System.out.println("HashMap is empty");
        }
        for (int i = 0; i < capacity; i++) {
            if (buckets[i] != null) {
                Node<Entry<K, V>> current = buckets[i].getHead();
                while (current != null) {
                    Entry<K, V> entry = current.getData();
                    System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
                    current = current.getNext();
                }
            }
        }
    }

    @Override
    public void clear() {
        buckets = new LinkedListImpl[capacity];
        size = 0;
    }

    @Override
    public void remove(K key, V value) {
        int hash = key.hashCode();
        int index = hash % capacity;
        if (buckets[index] != null) {
            Node<Entry<K, V>> current = buckets[index].getHead();
            Node<Entry<K, V>> prev = null;
            while (current != null) {
                Entry<K, V> entry = current.getData();
                if(entry.getKey().equals(key) && entry.getValue().equals(value)) {
                    if (prev == null) {
                        buckets[index].setHead(current.getNext());
                    } else {
                        prev.setNext(current.getNext());
                    }
                    size--;
                    return;
                }
                prev = current;
                current = current.getNext();
            }
        }
    }

    @Override
    public String toString() {
        return "HashMapImpl{" +
                "buckets=" + Arrays.toString(buckets) +
                ", size=" + size +
                ", capacity=" + capacity +
                '}';
    }
}
