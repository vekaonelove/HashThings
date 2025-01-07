package hashTableTests;

import com.kseniya.hash.dataStructures.hashTable.impl.HashTableImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HashTableTest{

    @Test
    void testAddAndGetElement() {
        HashTableImpl<String, Integer> hashTable = new HashTableImpl<>();
        hashTable.add("key1", 1);
        hashTable.add("key2", 2);

        assertEquals(1, hashTable.getElement("key1"));
        assertEquals(2, hashTable.getElement("key2"));
        assertNull(hashTable.getElement("key3")); // Non-existing key
    }

    @Test
    void testPop() {
        HashTableImpl<String, Integer> hashTable = new HashTableImpl<>();
        hashTable.add("key1", 1);
        hashTable.add("key2", 2);

        assertEquals(1, hashTable.pop("key1"));
        assertNull(hashTable.getElement("key1"));
        assertEquals(2, hashTable.getElement("key2"));
    }

    @Test
    void testGetOrDefault() {
        HashTableImpl<String, Integer> hashTable = new HashTableImpl<>();
        hashTable.add("key1", 1);

        assertEquals(1, hashTable.getOrDefault("key1", 0).get());
        assertEquals(0, hashTable.getOrDefault("key2", 0).get());
    }

    @Test
    void testRemove() {
        HashTableImpl<String, Integer> hashTable = new HashTableImpl<>();
        hashTable.add("key1", 1);
        hashTable.add("key2", 2);

        hashTable.remove("key1", 1);
        assertNull(hashTable.getElement("key1"));
        assertEquals(2, hashTable.getElement("key2"));
    }

    @Test
    void testReplace() {
        HashTableImpl<String, Integer> hashTable = new HashTableImpl<>();
        hashTable.add("key1", 1);

        hashTable.replace("key1", 1, 100);
        assertEquals(100, hashTable.getElement("key1"));

        hashTable.replace("key1", 200, 300); // Non-matching old value
        assertEquals(100, hashTable.getElement("key1")); // Should not replace
    }

    @Test
    void testClear() {
        HashTableImpl<String, Integer> hashTable = new HashTableImpl<>();
        hashTable.add("key1", 1);
        hashTable.add("key2", 2);

        hashTable.clear();
        assertEquals(0, hashTable.getSize());
        assertNull(hashTable.getElement("key1"));
        assertNull(hashTable.getElement("key2"));
    }

    @Test
    void testResizeToBinaryTree() {
        HashTableImpl<Integer, String> hashTable = new HashTableImpl<>();
        for (int i = 0; i < 9; i++) {
            hashTable.add(i, "value" + i);
        }

        // Verify that the bucket containing the 9th element is converted to a binary tree
        // Additional implementation-specific checks might be required based on tree structure
        assertEquals("value8", hashTable.getElement(8));
        assertEquals(9, hashTable.getSize());
    }
}
