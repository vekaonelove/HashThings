package hashThings.impl;
import hashThings.Entry;
import hashThings.HashMap;
import linkedList.LinkedList;
import linkedList.Node;
import linkedList.impl.LinkedListImpl;

import java.util.Arrays;

public class HashMapImpl<K,V> implements HashMap<K,V> {
    private LinkedListImpl<Entry<K, V>>[] buckets;
    private int size;
    private int capacity = 16; //default capacity - amount of buckets

    public LinkedList<Entry<K,V>>[] getBuckets() {
        return buckets;
    }

    public void setBuckets(LinkedListImpl<Entry<K,V>>[] buckets) {
        this.buckets = buckets;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void add(K key, V value) {
        int hash = key.hashCode();
        int index = hash % capacity;
        if (buckets[index] == null) {
            buckets[index] = new LinkedListImpl<>();
        }
        Entry<K, V> entry = new Entry<>(key, value, hash);
        buckets[index].addToEnd(entry);
        size++;
    }

    @Override
    public V getElement(K key){
        for(int i=0;i<capacity;i++){
            if(buckets[i] != null){
                Entry<K,V> entry = buckets[i].getHead().getData();
                if(entry.getKey().equals(key)){
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public V pop(K key) {
        int hash = key.hashCode();
        int index = hash % capacity;
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
        return null;
    }

    @Override
    public void printHashMap() {
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
                if (entry.getKey().equals(key) && entry.getValue().equals(value)) {
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
    public void replace(K key, V value) {
        int hash = key.hashCode();
        int index = hash % capacity;
        if (buckets[index] != null) {
            Node<Entry<K, V>> current = buckets[index].getHead();
            while (current != null) {
                Entry<K, V> entry = current.getData();
                if (entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return;
                }
                current = current.getNext();
            }
        }
        add(key, value);
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
