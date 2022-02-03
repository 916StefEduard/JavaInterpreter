package model.adt;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Sem implements ISem{

    private Map<Integer, Pair<Integer, List<Integer>>> map;
    private Stack<Integer> freeLocations;

    public Sem() {
        this.map = new HashMap<>();
        this.freeLocations = new Stack<>();
        freeLocations.add(1);
    }

    @Override
    public Pair<Integer, List<Integer>> lookup(Integer key) {
        synchronized (this) {
            return map.get(key);
        }
    }

    @Override
    public boolean isDefined(Integer key) {
        synchronized (this){
            return map.containsKey(key);
        }
    }

    @Override
    public void add(Integer key, Pair<Integer, List<Integer>> value) {
        synchronized (this){
            map.put(key, value);
        }
    }

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        synchronized (this){
            return map;
        }
    }

    @Override
    public void setContent(Map<Integer, Pair<Integer, List<Integer>>> content) {
        synchronized (this){
            this.map = content;
        }
    }

    @Override
    public void remove(Integer key) {
        synchronized (this){
            map.remove(key);
        }
    }

    @Override
    public Integer getFreeLocation() {
        synchronized (this){
            Integer freeLocation = freeLocations.pop();
            if(freeLocations.isEmpty())
                freeLocations.add(freeLocation+1);
            return freeLocation;
        }
    }

    @Override
    public void update(Integer key, Pair<Integer, List<Integer>> value) {
        synchronized (this){
            map.replace(key, value);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Dict{");
        map.forEach((key,value) -> stringBuilder.append(key).append("->").append(value).append(" "));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
