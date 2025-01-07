package linkedListTests;

import com.kseniya.hash.dataStructures.linkedList.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NodeTest {

    @Test
    public void testNodeCreation() {
        Node<String> node = new Node<>("data");
        assertEquals("data", node.getData());
        assertNull(node.getNext());
    }

    @Test
    public void testSetData() {
        Node<Integer> node = new Node<>(11);
        node.setData(22);
        assertEquals(22, node.getData());
    }

    @Test
    public void testSetNext() {
        Node<String> node1 = new Node<>("data1");
        Node<String> node2 = new Node<>("data2");
        node1.setNext(node2);
        assertEquals(node2, node1.getNext()); // Check if next node is set correctly
    }

    @Test
    public void testGetNext() {
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        node1.setNext(node2);
        assertEquals(node2, node1.getNext()); // Check if next node is retrieved correctly
    }
}
