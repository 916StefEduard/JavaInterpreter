package model.adt;

import java.util.Map;

public interface IDict<K, V> {
    void add(K v1, V v2);
    void update(K v1, V v2);
    void remove(K id);
    V lookup(K id);
    Map<K, V> getContent();
    boolean isDefined(K id);
    String keysToString();
}
