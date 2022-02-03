package model.adt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyDict<K, V> implements IDict<K, V> {
    private final Map<K, V> dictionary;

    public MyDict() {
        dictionary = new ConcurrentHashMap<>();
    }

    @Override
    public void add(K v1, V v2) {
        dictionary.put(v1, v2);
    }

    @Override
    public void update(K v1, V v2) {
        dictionary.put(v1, v2);
    }

    @Override
    public void remove(K id) {
        dictionary.remove(id);
    }

    @Override
    public V lookup(K id) {
        return dictionary.get(id);
    }

    @Override
    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public boolean isDefined(K id) {
        return dictionary.containsKey(id);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<K, V> entry: dictionary.entrySet()) {
            K key = entry.getKey();
            V val = entry.getValue();
            stringBuilder.append(String.format("%s -> %s\n", key.toString(), val.toString()));
        }
        return stringBuilder.toString();
    }

    public String keysToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (K key : dictionary.keySet()) {
            stringBuilder.append(String.format("%s\n", key.toString()));
        }
        return stringBuilder.toString();
    }
}
