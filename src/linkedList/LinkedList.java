package linkedList;

public interface LinkedList<T> {
    Node<T> getElementByIndex(int elementIndex);

    void addToEnd(T element);
    void addToHead(T element);
    void add(int index, T element);

    void remove(int index);
    void revert();

    void printList();
}
