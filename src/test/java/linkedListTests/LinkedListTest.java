package linkedListTests;

import com.kseniya.hash.dataStructures.linkedList.Node;
import com.kseniya.hash.dataStructures.linkedList.impl.LinkedListImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {
    private LinkedListImpl<String> list;

    @BeforeEach
    public void setUp() {
        list = new LinkedListImpl<>();
    }

    @Test
    public void testGetElementByIndex() {
        list.addToEnd("data1");
        list.addToEnd("data2");
        Node<String> node = list.getElementByIndex(1);
        assertEquals("data2", node.getData());
    }

    @Test
    public void testAddToEnd() {
        list.addToEnd("data1");
        list.addToEnd("data2");
        assertEquals(2, list.getSize());
        assertEquals("data1", list.getElementByIndex(0).getData());
        assertEquals("data2", list.getElementByIndex(1).getData());
    }

    @Test
    public void testAddToHead() {
        list.addToHead("data1");
        list.addToHead("data2");
        assertEquals(2, list.getSize());
        assertEquals("data2", list.getElementByIndex(0).getData());
        assertEquals("data1", list.getElementByIndex(1).getData());
    }

    @Test
    public void testAdd() {
        list.addToEnd("data1");
        list.add(0, "data2");
        assertEquals(2, list.getSize());
        assertEquals("data2", list.getElementByIndex(0).getData());
        assertEquals("data1", list.getElementByIndex(1).getData());
    }

    @Test
    public void testRemove() {
        list.addToEnd("data1");
        list.addToEnd("data2");
        list.remove(0);
        assertEquals(1, list.getSize());
        assertEquals("data2", list.getElementByIndex(0).getData());
    }

    @Test
    public void testRevert() {
        list.addToEnd("data1");
        list.addToEnd("data2");
        list.revert();
        assertEquals("data2", list.getElementByIndex(0).getData());
        assertEquals("data1", list.getElementByIndex(1).getData());
    }

    @Test
    public void testPrintList() {
        list.addToEnd("data1");
        list.addToEnd("data2");
        list.printList();
    }
}