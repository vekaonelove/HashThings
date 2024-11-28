package hashTable.impl;

import hashTable.Entry;
import hashTable.HashTable;
import linkedList.Node;
import linkedList.impl.LinkedListImpl;

public class HashTableImpl<K, V> implements HashTable<K, V> {
    private LinkedListImpl<LinkedListImpl<Entry<K, V>>> buckets;
    private int size;
    private int capacity = 8;

    public HashTableImpl() {
        this.buckets = new LinkedListImpl<>();
        for (int i = 0; i < capacity; i++) {
            buckets.addToEnd(new LinkedListImpl<>());
        }
    }

    public LinkedListImpl<LinkedListImpl<Entry<K, V>>> getBuckets() {
        return buckets;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void add(K key, V value) {
        Entry<K, V> entry = new Entry<>(key);
        int index = entry.getHashKey() % capacity;
        Node<LinkedListImpl<Entry<K, V>>> bucketNode = buckets.getElementByIndex(index);
        LinkedListImpl<Entry<K, V>> bucket = (bucketNode != null) ? bucketNode.getData() : null;
        if (bucket == null) {
            bucket = new LinkedListImpl<>();
            buckets.addToEnd(bucket);
        }
        bucket.addToEnd(entry);
        size++;
    }

    @Override
    public V getElement(K key) {
        Entry<K, V> tempEntry = new Entry<>(key, null); //just to get hashKey
        int index = tempEntry.getHashKey() % capacity;
        Node<LinkedListImpl<Entry<K, V>>> bucketNode = buckets.getElementByIndex(index);
        LinkedListImpl<Entry<K, V>> bucket = (bucketNode != null) ? bucketNode.getData() : null;
        if (bucket != null) {
            Node<Entry<K, V>> current = bucket.getHead();
            while (current != null) {
                Entry<K, V> entry = current.getData();
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
                current = current.getNext();
            }
        }
        return null;
    }


    @Override
    public V pop(K key) {
        Entry<K, V> tempEntry = new Entry<>(key, null); // just to get hashKey
        int index = tempEntry.getHashKey() % capacity;
        Node<LinkedListImpl<Entry<K, V>>> bucketNode = buckets.getElementByIndex(index);

        LinkedListImpl<Entry<K, V>> bucket = (bucketNode != null) ? bucketNode.getData() : null;
        if (bucket != null) {
            Node<Entry<K, V>> current = bucket.getHead();
            Node<Entry<K, V>> prev = null;
            while (current != null) {
                Entry<K, V> entry = current.getData();
                if (entry.getKey().equals(key)) {
                    if (prev == null) {
                        bucket.setHead(current.getNext());
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
        return null;
    }

    @Override
    public void printHashMap() {
        if (size > 0) {
            System.out.println("HashMap:");
        } else {
            System.out.println("HashMap is empty");
        }
        for (int i = 0; i < capacity; i++) {
            Node<LinkedListImpl<Entry<K, V>>> bucketNode = buckets.getElementByIndex(i);
            LinkedListImpl<Entry<K, V>> bucket = (bucketNode != null) ? bucketNode.getData() : null;
            if (bucket != null) {
                Node<Entry<K, V>> current = bucket.getHead();
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
        buckets = new LinkedListImpl<>();
        for (int i = 0; i < capacity; i++) {
            buckets.addToEnd(new LinkedListImpl<>());
        }
        size = 0;
    }


    @Override
    public void replace(K key, V value1, V value2) {
        Entry<K, V> entry = new Entry<>(key, value1);
        int index = entry.getHashKey() % capacity;
        Node<LinkedListImpl<Entry<K, V>>> bucketNode = buckets.getElementByIndex(index);
        LinkedListImpl<Entry<K, V>> bucket = (bucketNode != null) ? bucketNode.getData() : null;
        if (bucket != null) {
            Node<Entry<K, V>> current = bucket.getHead();
            while (current != null) {
                Entry<K, V> currentEntry = current.getData();
                if (currentEntry.getKey().equals(key) && currentEntry.getValue().equals(value1)) {
                    currentEntry.setValue(value2);
                    return;
                }
                current = current.getNext();
            }
        }
    }

    @Override
    public void remove(K key, V value) {
        Entry<K, V> tempEntry = new Entry<>(key, value);
        int index = tempEntry.getHashKey() % capacity;
        Node<LinkedListImpl<Entry<K, V>>> bucketNode = buckets.getElementByIndex(index);
        LinkedListImpl<Entry<K, V>> bucket = (bucketNode != null) ? bucketNode.getData() : null;
        if (bucket != null) {
            Node<Entry<K, V>> current = bucket.getHead();
            Node<Entry<K, V>> prev = null;
            while (current != null) {
                Entry<K, V> entry = current.getData();
                if (entry.getKey().equals(key)) {
                    if (prev == null) {
                        bucket.setHead(current.getNext());
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
        return "HashTableImpl{" +
                "buckets=" + buckets +
                ", size=" + size +
                ", capacity=" + capacity +
                '}';
    }
}