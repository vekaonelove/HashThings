package hashTable;

public interface HashTable<K,V>{
    V getElement(K key);
    V pop(K key);

    void add(K key, V value);
    void clear();
    void remove(K key,V value);
    void replace(K key, V value1, V value2);
    void printHashMap();
}
