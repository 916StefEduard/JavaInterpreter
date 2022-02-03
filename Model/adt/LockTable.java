package model.adt;

import java.util.Collection;
import java.util.HashMap;

public class LockTable<K,V> implements ILockTable<K,V>{
    private HashMap<K,V> map;

    public LockTable(){
        map = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        this.map.put(key,value);
    }

    @Override
    public V get(K key) {
        return this.map.get(key);
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public Collection<K> keys() {
        return this.map.keySet();
    }

    @Override
    public void remove(K fd) {
        this.map.remove(fd);
    }

    @Override
    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public void update(K key, V newValue) {
        this.map.replace(key,newValue);
    }
}
