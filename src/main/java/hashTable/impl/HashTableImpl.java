package hashTable.impl;

import hashTable.Entry;
import hashTable.HashTable;
import linkedList.Node;
import linkedList.impl.LinkedListImpl;
import binaryTree.BinaryTree;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class HashTableImpl<K extends Comparable<K>, V> implements HashTable<K, V> {
    private LinkedListImpl<Object> buckets;
    private int size;
    private final int capacity = 8;
    private static final int BUCKET_THRESHOLD = 8;

    public HashTableImpl() {
        this.buckets = new LinkedListImpl<>();
        for (int i = 0; i < capacity; i++) {
            buckets.addToEnd(new LinkedListImpl<>());
        }
    }

    public LinkedListImpl<Object> getBuckets() {
        return buckets;
    }

    public void setBuckets(LinkedListImpl<Object> buckets) {
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

    @Override
    public void add(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value);
        int index = entry.getHashKey() % capacity;
        Node<Object> bucketNode = buckets.getElementByIndex(index);
        Object bucket = (bucketNode != null) ? bucketNode.getData() : null;

        if (bucket instanceof LinkedListImpl) {
            @SuppressWarnings("unchecked")
            LinkedListImpl<Entry<K, V>> linkedList = (LinkedListImpl<Entry<K, V>>) bucket;
            if (linkedList.getSize() >= BUCKET_THRESHOLD) {
                BinaryTree<K, V> tree = new BinaryTree<>();
                Node<Entry<K, V>> current = linkedList.getHead();
                while (current != null) {
                    Entry<K, V> existingEntry = current.getData();
                    tree.add(existingEntry.getKey(), existingEntry.getValue());
                    current = current.getNext();
                }
                tree.add(key, value);
                buckets.add(index, tree);
            } else {
                linkedList.addToEnd(entry);
            }
        } else if (bucket instanceof BinaryTree) {
            @SuppressWarnings("unchecked")
            BinaryTree<K, V> tree = (BinaryTree<K, V>) bucket;
            tree.add(key, value);
        } else {
            LinkedListImpl<Entry<K, V>> newLinkedList = new LinkedListImpl<>();
            newLinkedList.addToEnd(entry);
            buckets.add(index, newLinkedList);
        }
        size++;
    }

    @Override
    public V pop(K key) {
        int index = key.hashCode() % capacity;
        Node<Object> bucketNode = buckets.getElementByIndex(index);
        Object bucket = (bucketNode != null) ? bucketNode.getData() : null;

        if (bucket instanceof LinkedListImpl) {
            LinkedListImpl<Entry<K, V>> linkedList = (LinkedListImpl<Entry<K, V>>) bucket;
            Node<Entry<K, V>> current = linkedList.getHead();
            Node<Entry<K, V>> previous = null;
            while (current != null) {
                Entry<K, V> entry = current.getData();
                if (entry.getKey().equals(key)) {
                    V value = entry.getValue();
                    if (previous == null) {
                        linkedList.setHead(current.getNext());
                    } else {
                        previous.setNext(current.getNext());
                    }
                    size--;
                    return value;
                }
                previous = current;
                current = current.getNext();
            }
        } else if (bucket instanceof BinaryTree) {
            BinaryTree<K, V> tree = (BinaryTree<K, V>) bucket;
            V value = tree.get(key);
            if (value != null) {
                tree.remove(key);
                size--;
                return value;
            }
        }
        return null;
    }

    @Override
    public V getElement(K key) {
        int index = key.hashCode() % capacity;
        Node<Object> bucketNode = buckets.getElementByIndex(index);
        Object bucket = (bucketNode != null) ? bucketNode.getData() : null;

        if (bucket instanceof LinkedListImpl) {
            LinkedListImpl<Entry<K, V>> linkedList = (LinkedListImpl<Entry<K, V>>) bucket;
            Node<Entry<K, V>> current = linkedList.getHead();
            while (current != null) {
                Entry<K, V> entry = current.getData();
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
                current = current.getNext();
            }
        } else if (bucket instanceof BinaryTree) {
            BinaryTree<K, V> tree = (BinaryTree<K, V>) bucket;
            return tree.get(key);
        }
        return null;
    }

    @Override
    public Optional<V> getOrDefault(K key, V defaultValue) {
        V value = getElement(key);
        return Optional.ofNullable(value != null ? value : defaultValue);
    }

    @Override
    public void remove(K key, V value) {
        int index = key.hashCode() % capacity;
        Node<Object> bucketNode = buckets.getElementByIndex(index);
        Object bucket = (bucketNode != null) ? bucketNode.getData() : null;

        if (bucket instanceof LinkedListImpl) {
            LinkedListImpl<Entry<K, V>> linkedList = (LinkedListImpl<Entry<K, V>>) bucket;
            Node<Entry<K, V>> current = linkedList.getHead();
            Node<Entry<K, V>> previous = null;
            while (current != null) {
                Entry<K, V> entry = current.getData();
                if (entry.getKey().equals(key) && entry.getValue().equals(value)) {
                    if (previous == null) {
                        linkedList.setHead(current.getNext());
                    } else {
                        previous.setNext(current.getNext());
                    }
                    size--;
                    return;
                }
                previous = current;
                current = current.getNext();
            }
        } else if (bucket instanceof BinaryTree) {
            BinaryTree<K, V> tree = (BinaryTree<K, V>) bucket;
            V treeValue = tree.get(key);
            if (treeValue != null && treeValue.equals(value)) {
                tree.remove(key);
                size--;
            }
        }
    }

    @Override
    public void clear() {
        this.buckets = new LinkedListImpl<>();
        for (int i = 0; i < capacity; i++) {
            buckets.addToEnd(new LinkedListImpl<>());
        }
        size = 0;
    }

    @Override
    public void replace(K key, V oldValue, V newValue) {
        int index = key.hashCode() % capacity;
        Node<Object> bucketNode = buckets.getElementByIndex(index);
        Object bucket = (bucketNode != null) ? bucketNode.getData() : null;

        if (bucket instanceof LinkedListImpl) {
            LinkedListImpl<Entry<K, V>> linkedList = (LinkedListImpl<Entry<K, V>>) bucket;
            Node<Entry<K, V>> current = linkedList.getHead();
            while (current != null) {
                Entry<K, V> entry = current.getData();
                if (entry.getKey().equals(key) && entry.getValue().equals(oldValue)) {
                    entry.setValue(newValue);
                    return;
                }
                current = current.getNext();
            }
        } else if (bucket instanceof BinaryTree) {
            BinaryTree<K, V> tree = (BinaryTree<K, V>) bucket;
            V treeValue = tree.get(key);
            if (treeValue != null && treeValue.equals(oldValue)) {
                tree.replace(key, newValue);
            }
        }
    }
}