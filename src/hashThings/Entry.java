package hashThings;

public class Entry<K,V> {
    final K key;
    V value;
    Entry<K,V> next;
    final int hash;

    public Entry(K key, V value, int hash) {
        this.key = key;
        this.value = value;
        this.hash = hash;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Entry<K,V> getNext() {
        return next;
    }

    public void setNext(Entry<K,V> next) {
        this.next = next;
    }

    public int getHash() {
        return hash;
    }
}
