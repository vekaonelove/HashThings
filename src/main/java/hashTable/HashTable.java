package hashTable;

import binaryTree.BinaryTree;
import linkedList.Node;
import linkedList.impl.LinkedListImpl;

import java.util.Optional;

public interface HashTable<K extends Comparable<K>, V> {
    V getElement(K key);
    V pop(K key);
    Optional<?> getOrDefault(K key, V value);

    void add(K key, V value);
    void clear();
    void remove(K key, V value);
    void replace(K key, V value1, V value2);

    default void printHashTable() {
        for (int i = 0; i < getCapacity(); i++) {
            Node<Object> bucketNode = getBuckets().getElementByIndex(i);
            Object bucket = (bucketNode != null) ? bucketNode.getData() : null;

            if (bucket instanceof LinkedListImpl) {
                LinkedListImpl<Entry<K, V>> linkedList = (LinkedListImpl<Entry<K, V>>) bucket;
                Node<Entry<K, V>> current = linkedList.getHead();
                while (current != null) {
                    Entry<K, V> entry = current.getData();
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                    current = current.getNext();
                }
            } else if (bucket instanceof BinaryTree) {
                BinaryTree<K, V> tree = (BinaryTree<K, V>) bucket;
                tree.inOrderTraversal((key, value) -> System.out.println(key + " : " + value));
            }
        }
    }

    LinkedListImpl<Object> getBuckets();
    int getCapacity();
}