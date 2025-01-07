package hashTableTests;

import com.kseniya.hash.dataStructures.hashTable.Entry;
import com.kseniya.hash.dataStructures.hashTable.impl.HashMapImpl;
import com.kseniya.hash.dataStructures.linkedList.impl.LinkedListImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class HashMapImplTest {
    private HashMapImpl<String, Integer> hashMap;

    @BeforeEach
    void setUp() {
        @SuppressWarnings("unchecked")
        LinkedListImpl<Entry<String, Integer>>[] buckets = new LinkedListImpl[8];
        hashMap = new HashMapImpl<>(buckets);
    }

    @Test
    void testAddAndGetElement() {
        hashMap.add("key1", 1);
        hashMap.add("key2", 2);

        assertEquals(1, hashMap.getElement("key1"));
        assertEquals(2, hashMap.getElement("key2"));
        assertNull(hashMap.getElement("key3")); // Non-existent key
    }

    @Test
    void testGetOrDefault() {
        hashMap.add("key1", 1);

        assertEquals(Optional.of(1), hashMap.getOrDefault("key1", 0));
        assertEquals(Optional.of(0), hashMap.getOrDefault("key2", 0)); // Non-existent key
    }

    @Test
    void testReplace() {
        hashMap.add("key1", 1);

        hashMap.replace("key1", 1, 10);
        assertEquals(10, hashMap.getElement("key1"));

        hashMap.replace("key1", 5, 20); // Should not replace
        assertEquals(10, hashMap.getElement("key1"));
    }

    @Test
    void testPop() {
        hashMap.add("key1", 1);
        hashMap.add("key2", 2);

        assertEquals(1, hashMap.pop("key1"));
        assertNull(hashMap.getElement("key1")); // Key should be removed
        assertEquals(1, hashMap.getSize()); // Size should decrease
    }

    @Test
    void testRemove() {
        hashMap.add("key1", 1);
        hashMap.add("key2", 2);

        hashMap.remove("key1", 1);
        assertNull(hashMap.getElement("key1")); // Key should be removed
        assertEquals(1, hashMap.getSize()); // Size should decrease

        // Attempting to remove non-matching value
        hashMap.remove("key2", 3);
        assertEquals(2, hashMap.getElement("key2")); // Key should remain
    }

    @Test
    void testClear() {
        hashMap.add("key1", 1);
        hashMap.add("key2", 2);

        hashMap.clear();
        assertEquals(0, hashMap.getSize());
        assertNull(hashMap.getElement("key1"));
        assertNull(hashMap.getElement("key2"));
    }

    @Test
    void testPrintHashTable() {
        hashMap.add("key1", 1);
        hashMap.add("key2", 2);

        assertDoesNotThrow(hashMap::printHashTable);
    }

    @Test
    void testCapacity() {
        assertEquals(8, hashMap.getCapacity());
        hashMap.setCapacity(16);
        assertEquals(16, hashMap.getCapacity());
    }

    @Test
    void testSize() {
        assertEquals(0, hashMap.getSize());
        hashMap.add("key1", 1);
        assertEquals(1, hashMap.getSize());
    }

    @Test
    void testToString() {
        hashMap.add("key1", 1);
        hashMap.add("key2", 2);

        String result = hashMap.toString();
        assertTrue(result.contains("buckets="));
        assertTrue(result.contains("size=2"));
    }
}
