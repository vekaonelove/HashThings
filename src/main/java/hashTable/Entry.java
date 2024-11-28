package hashTable;

import com.google.common.hash.Hashing;
import java.util.Objects;

public class Entry<K, V> {
    private final int hashKey;
    private final K key;
    private V value;
    Entry<K, V> next;

    public Entry(K key, V value) {
        this.hashKey = Hashing.murmur3_32_fixed().hashInt(key.hashCode()).asInt();
        this.key = key;
        this.value = value;
    }

    public Entry(K key) {
        this.hashKey = Hashing.murmur3_32_fixed().hashInt(key.hashCode()).asInt();
        this.key = key;
        this.value = null;
    }

    public int getHashKey() {
        return hashKey;
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

    public Entry<K, V> getNext() {
        return next;
    }

    public void setNext(Entry<K, V> next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entry<?, ?> entry = (Entry<?, ?>) o;
        return hashKey == entry.hashKey && Objects.equals(key, entry.key) && Objects.equals(value, entry.value) && Objects.equals(next, entry.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashKey, key, value, next);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "hashKey=" + hashKey +
                ", key=" + key +
                ", value=" + value +
                ", next=" + next +
                '}';
    }
}