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

    @Override
    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void add(K key, V value) {
        int index = calculateIndex(key);
        Object bucket = getBucket(index);

        Entry<K, V> newEntry = new Entry<>(key, value);

        if (bucket instanceof LinkedListImpl) {
            handleAddToLinkedList((LinkedListImpl<Entry<K, V>>) bucket, newEntry, index);
        } else if (bucket instanceof BinaryTree) {
            ((BinaryTree<K, V>) bucket).add(key, value);
        } else {
            LinkedListImpl<Entry<K, V>> newBucket = new LinkedListImpl<>();
            newBucket.addToEnd(newEntry);
            buckets.add(index, newBucket);
        }
        size++;
    }

    @Override
    public V pop(K key) {
        int index = calculateIndex(key);
        Object bucket = getBucket(index);

        if (bucket instanceof LinkedListImpl) {
            return removeFromLinkedList((LinkedListImpl<Entry<K, V>>) bucket, key);
        } else if (bucket instanceof BinaryTree) {
            BinaryTree<K, V> tree = (BinaryTree<K, V>) bucket;
            V value = tree.get(key);
            if (value != null) {
                tree.remove(key);
                size--;
            }
            return value;
        }
        return null;
    }

    @Override
    public V getElement(K key) {
        int index = calculateIndex(key);
        Object bucket = getBucket(index);

        if (bucket instanceof LinkedListImpl) {
            return getFromLinkedList((LinkedListImpl<Entry<K, V>>) bucket, key);
        } else if (bucket instanceof BinaryTree) {
            return ((BinaryTree<K, V>) bucket).get(key);
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
        int index = calculateIndex(key);
        Object bucket = getBucket(index);

        if (bucket instanceof LinkedListImpl) {
            LinkedListImpl<Entry<K, V>> linkedList = (LinkedListImpl<Entry<K, V>>) bucket;
            removeFromLinkedListByValue(linkedList, key, value);
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
        int index = calculateIndex(key);
        Object bucket = getBucket(index);

        if (bucket instanceof LinkedListImpl) {
            replaceInLinkedList((LinkedListImpl<Entry<K, V>>) bucket, key, oldValue, newValue);
        } else if (bucket instanceof BinaryTree) {
            BinaryTree<K, V> tree = (BinaryTree<K, V>) bucket;
            V treeValue = tree.get(key);
            if (treeValue != null && treeValue.equals(oldValue)) {
                tree.replace(key, newValue);
            }
        }
    }

    // ===================== Helper Methods ===================== //

    private int calculateIndex(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    private Object getBucket(int index) {
        Node<Object> bucketNode = buckets.getElementByIndex(index);
        return (bucketNode != null) ? bucketNode.getData() : null;
    }

    private void handleAddToLinkedList(LinkedListImpl<Entry<K, V>> linkedList, Entry<K, V> newEntry, int index) {
        if (linkedList.getSize() >= BUCKET_THRESHOLD) {
            BinaryTree<K, V> tree = new BinaryTree<>();
            Node<Entry<K, V>> current = linkedList.getHead();
            while (current != null) {
                tree.add(current.getData().getKey(), current.getData().getValue());
                current = current.getNext();
            }
            tree.add(newEntry.getKey(), newEntry.getValue());
            buckets.add(index, tree);
        } else {
            linkedList.addToEnd(newEntry);
        }
    }

    private V getFromLinkedList(LinkedListImpl<Entry<K, V>> linkedList, K key) {
        Node<Entry<K, V>> current = linkedList.getHead();
        while (current != null) {
            if (current.getData().getKey().equals(key)) {
                return current.getData().getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    private V removeFromLinkedList(LinkedListImpl<Entry<K, V>> linkedList, K key) {
        Node<Entry<K, V>> current = linkedList.getHead();
        Node<Entry<K, V>> previous = null;

        while (current != null) {
            if (current.getData().getKey().equals(key)) {
                V value = current.getData().getValue();
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
        return null;
    }

    private void removeFromLinkedListByValue(LinkedListImpl<Entry<K, V>> linkedList, K key, V value) {
        Node<Entry<K, V>> current = linkedList.getHead();
        Node<Entry<K, V>> previous = null;

        while (current != null) {
            if (current.getData().getKey().equals(key) && current.getData().getValue().equals(value)) {
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
    }

    private void replaceInLinkedList(LinkedListImpl<Entry<K, V>> linkedList, K key, V oldValue, V newValue) {
        Node<Entry<K, V>> current = linkedList.getHead();

        while (current != null) {
            if (current.getData().getKey().equals(key) && current.getData().getValue().equals(oldValue)) {
                current.getData().setValue(newValue);
                return;
            }
            current = current.getNext();
        }
    }
}
