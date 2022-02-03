package model.adt;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface ISem {
    Pair<Integer, List<Integer>> lookup(Integer key);
    boolean isDefined(Integer key);
    void add(Integer key, Pair<Integer, List<Integer>> value);
    Map<Integer, Pair<Integer, List<Integer>>> getContent();
    void setContent(Map<Integer, Pair<Integer, List<Integer>>> content);
    void remove(Integer key);
    Integer getFreeLocation();
    void update(Integer key, Pair<Integer, List<Integer>> value);
    String toString();
}
