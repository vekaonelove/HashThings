package binaryTreeTests;

import static org.junit.jupiter.api.Assertions.*;

import com.kseniya.hash.dataStructures.binaryTree.BinaryTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryTreeTest {
    private BinaryTree<String, Integer> tree;

    @BeforeEach
    public void setUp() {
        tree = new BinaryTree<>();
    }

    @Test
    public void testAddAndGet() {
        tree.add("key1", 1);
        tree.add("key2", 2);
        tree.add("key3", 3);

        assertEquals(1, tree.get("key1"));
        assertEquals(2, tree.get("key2"));
        assertEquals(3, tree.get("key3"));
    }

    @Test
    public void testGetNonExistentKey() {
        tree.add("key1", 1);

        assertNull(tree.get("nonexistent"));
    }

    @Test
    public void testInOrderTraversal() {
        tree.add("key1", 1);
        tree.add("key2", 2);
        tree.add("key3", 3);

        List<String> expectedKeys = Arrays.asList("key1", "key2", "key3");
        List<Integer> expectedValues = Arrays.asList(1, 2, 3);

        // Capture the output of the traversal
        StringBuilder keysOutput = new StringBuilder();
        StringBuilder valuesOutput = new StringBuilder();

        tree.inOrderTraversal((key, value) -> {
            keysOutput.append(key).append(" ");
            valuesOutput.append(value).append(" ");
        });

        // Convert the space-separated string into actual lists
        List<String> actualKeys = Arrays.asList(keysOutput.toString().split(" "));
        List<Integer> actualValues = Arrays.stream(valuesOutput.toString().split(" "))
                .map(Integer::parseInt)  // Convert String to Integer
                .collect(Collectors.toList());

        assertEquals(expectedKeys, actualKeys);
        assertEquals(expectedValues, actualValues);
    }

    @Test
    public void testRemove() {
        tree.add("key1", 1);
        tree.add("key2", 2);
        tree.add("key3", 3);

        tree.remove("key2");

        assertNull(tree.get("key2"));
        assertEquals(1, tree.get("key1"));
        assertEquals(3, tree.get("key3"));
    }

    @Test
    public void testReplace() {
        tree.add("key1", 1);
        tree.add("key2", 2);
        tree.add("key3", 3);

        tree.replace("key2", 20);

        assertEquals(20, tree.get("key2"));
    }

    @Test
    public void testRemoveLeafNode() {
        tree.add("key1", 1);
        tree.add("key2", 2);
        tree.add("key3", 3);

        tree.remove("key3");

        assertNull(tree.get("key3"));
    }

    @Test
    public void testRemoveNodeWithOneChild() {
        tree.add("key1", 1);
        tree.add("key2", 2);
        tree.add("key3", 3);

        tree.remove("key2");

        assertNull(tree.get("key2"));
        assertEquals(1, tree.get("key1"));
        assertEquals(3, tree.get("key3"));
    }

    @Test
    public void testRemoveNodeWithTwoChildren() {
        tree.add("key1", 1);
        tree.add("key2", 2);
        tree.add("key3", 3);

        tree.remove("key1");

        assertNull(tree.get("key1"));
        assertEquals(2, tree.get("key2"));
        assertEquals(3, tree.get("key3"));
    }
}
