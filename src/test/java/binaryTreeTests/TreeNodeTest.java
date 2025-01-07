package binaryTreeTests;

import com.kseniya.hash.dataStructures.binaryTree.TreeNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TreeNodeTest {

    private TreeNode<String, Integer> root;

    @Test
    void testConstructorAndGetters() {
        TreeNode<String, Integer> node = new TreeNode<>("key1", 10);

        assertEquals("key1", node.getKey());
        assertEquals(10, node.getValue());
        assertNull(node.getLeft());
        assertNull(node.getRight());
    }

    @Test
    void testSetKey() {
        TreeNode<String, Integer> node = new TreeNode<>("key1", 10);
        node.setKey("key2");

        assertEquals("key2", node.getKey());
    }

    @Test
    void testSetValue() {
        TreeNode<String, Integer> node = new TreeNode<>("key1", 10);
        node.setValue(20);

        assertEquals(20, node.getValue());
    }

    @Test
    void testSetLeftChild() {
        TreeNode<String, Integer> parent = new TreeNode<>("parent", 100);
        TreeNode<String, Integer> leftChild = new TreeNode<>("left", 50);

        parent.setLeft(leftChild);

        assertEquals(leftChild, parent.getLeft());
        assertEquals("left", parent.getLeft().getKey());
        assertEquals(50, parent.getLeft().getValue());
    }

    @Test
    void testSetRightChild() {
        TreeNode<String, Integer> parent = new TreeNode<>("parent", 100);
        TreeNode<String, Integer> rightChild = new TreeNode<>("right", 150);

        parent.setRight(rightChild);

        assertEquals(rightChild, parent.getRight());
        assertEquals("right", parent.getRight().getKey());
        assertEquals(150, parent.getRight().getValue());
    }

    @Test
    void testTreeStructure() {
        TreeNode<String, Integer> root = new TreeNode<>("root", 100);
        TreeNode<String, Integer> leftChild = new TreeNode<>("left", 50);
        TreeNode<String, Integer> rightChild = new TreeNode<>("right", 150);

        root.setLeft(leftChild);
        root.setRight(rightChild);

        assertEquals(leftChild, root.getLeft());
        assertEquals(rightChild, root.getRight());

        assertEquals("left", root.getLeft().getKey());
        assertEquals(50, root.getLeft().getValue());

        assertEquals("right", root.getRight().getKey());
        assertEquals(150, root.getRight().getValue());
    }

    @Test
    void testModifyChildren() {
        TreeNode<String, Integer> root = new TreeNode<>("root", 100);
        TreeNode<String, Integer> initialLeftChild = new TreeNode<>("initialLeft", 30);
        TreeNode<String, Integer> newLeftChild = new TreeNode<>("newLeft", 70);

        root.setLeft(initialLeftChild);
        assertEquals(initialLeftChild, root.getLeft());

        root.setLeft(newLeftChild);
        assertEquals(newLeftChild, root.getLeft());
        assertNotEquals(initialLeftChild, root.getLeft());
    }
}
