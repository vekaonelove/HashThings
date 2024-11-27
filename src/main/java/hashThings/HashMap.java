package hashThings;

public interface HashMap<K,V> {
    V getElement(K key);
    V pop(K key);

    void add(K key, V value);
    void clear();
    void remove(K key,V value);
    void replace(K key, V value);
    void printHashMap();
}
