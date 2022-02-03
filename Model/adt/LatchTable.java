package model.adt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LatchTable<K,V> implements ILatchTable<K,V> {
    private HashMap<K,V> map;

    public LatchTable(){
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
        return map.containsKey(key);
    }


    @Override
    public ILatchTable<K, V> clone() {
        var newDict = new LatchTable();
        for(var key:this.map.keySet()){
            newDict.put(key,this.map.get(key));
        }
        return newDict;
    }

    @Override
    public String toString(){
        String result = "";
        boolean ok = false;
        for(var pair:this.map.entrySet()){
            if(ok)
                result = result + "\n";
            result += pair.getKey().toString() + " -> " + pair.getValue();
            ok = true;
        }
        return result;
    }

    @Override
    public Map<K, V> toMap() {
        return this.map;
    }

    @Override
    public void update(K key, V newValue) {
        this.map.replace(key,newValue);
    }

    @Override
    public int getSize() {
        return this.map.size();
    }
}
