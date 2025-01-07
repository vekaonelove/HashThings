package com.kseniya.hash.dataStructures.binaryTree;

import java.util.function.BiConsumer;

public class BinaryTree<K extends Comparable<K>, V> {
    private TreeNode<K, V> root;

    public void add(K key, V value) {
        root = addRecursive(root, key, value);
    }

    private TreeNode<K, V> addRecursive(TreeNode<K, V> current, K key, V value) {
        if (current == null) {
            return new TreeNode<>(key, value);
        }

        if (key.compareTo(current.getKey()) < 0) {
            current.setLeft(addRecursive(current.getLeft(), key, value));
        } else if (key.compareTo(current.getKey()) > 0) {
            current.setRight(addRecursive(current.getRight(), key, value));
        } else {
            current.setValue(value);
        }

        return current;
    }

    public V get(K key) {
        return getRecursive(root, key);
    }

    private V getRecursive(TreeNode<K, V> current, K key) {
        if (current == null) {
            return null;
        }

        if (key.equals(current.getKey())) {
            return current.getValue();
        }

        return key.compareTo(current.getKey()) < 0
                ? getRecursive(current.getLeft(), key)
                : getRecursive(current.getRight(), key);
    }

    public void inOrderTraversal(BiConsumer<K, V> consumer) {
        inOrderTraversalRecursive(root, consumer);
    }

    private void inOrderTraversalRecursive(TreeNode<K, V> node, BiConsumer<K, V> consumer) {
        if (node != null) {
            inOrderTraversalRecursive(node.getLeft(), consumer);
            consumer.accept(node.getKey(), node.getValue());
            inOrderTraversalRecursive(node.getRight(), consumer);
        }
    }

    public void remove(K key) {
        root = removeRecursive(root, key);
    }

    private TreeNode<K, V> removeRecursive(TreeNode<K, V> current, K key) {
        if (current == null) {
            return null;
        }

        if (key.equals(current.getKey())) {
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            }
            if (current.getRight() == null) {
                return current.getLeft();
            }
            if (current.getLeft() == null) {
                return current.getRight();
            }
            K smallestKey = findSmallestKey(current.getRight());
            current.setKey(smallestKey);
            current.setValue(get(smallestKey));
            current.setRight(removeRecursive(current.getRight(), smallestKey));
            return current;
        }

        if (key.compareTo(current.getKey()) < 0) {
            current.setLeft(removeRecursive(current.getLeft(), key));
            return current;
        }

        current.setRight(removeRecursive(current.getRight(), key));
        return current;
    }

    private K findSmallestKey(TreeNode<K, V> root) {
        return root.getLeft() == null ? root.getKey() : findSmallestKey(root.getLeft());
    }

    public void replace(K key, V newValue) {
        replaceRecursive(root, key, newValue);
    }

    private void replaceRecursive(TreeNode<K, V> current, K key, V newValue) {
        if (current == null) {
            return;
        }

        if (key.equals(current.getKey())) {
            current.setValue(newValue);
        } else if (key.compareTo(current.getKey()) < 0) {
            replaceRecursive(current.getLeft(), key, newValue);
        } else {
            replaceRecursive(current.getRight(), key, newValue);
        }
    }
}