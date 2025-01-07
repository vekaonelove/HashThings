package hashTableTests;

import com.kseniya.hash.dataStructures.hashTable.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntryTest {

    private Entry<String, String> entry;

    @BeforeEach
    public void setUp() {
        entry = new Entry<>("key1", "value1");
    }

    @Test
    public void testGetKey() {
        assertEquals("key1", entry.getKey());
    }

    @Test
    public void testGetValue() {
        assertEquals("value1", entry.getValue());
    }

    @Test
    public void testSetValue() {
        entry.setValue("value2");
        assertEquals("value2", entry.getValue());
    }

    @Test
    public void testHashCode() {
        Entry<String, String> entry2 = new Entry<>("key1", "value1");
        assertEquals(entry.hashCode(), entry2.hashCode());
    }
}