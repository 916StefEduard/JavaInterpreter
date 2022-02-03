package model.adt;

import java.util.HashMap;
import java.util.Map;

public class CyclicBarrier<K,V> implements  ICyclicBarrier<K,V>{

    private Map<K,V> dict;

    public CyclicBarrier(){
        dict = new HashMap<>();
    }

    @Override
    public void add(K key, V value) {
        dict.put(key,value);
    }

    @Override
    public V get(K key) {
        return dict.get(key);
    }

    @Override
    public void update(K key, V value) {
        dict.put(key,value);
    }

    @Override
    public boolean contains(K key) {
        return dict.containsKey(key);
    }
}
