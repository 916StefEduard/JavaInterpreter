package model.adt;

import java.util.Collection;

public interface ILockTable<K,V> {
    void put(K key,V value);
    V get(K key);
    Collection<V> values();
    Collection<K> keys();
    void remove(K fd);
    boolean contains(K key);
    void update(K key,V newValue);
}
