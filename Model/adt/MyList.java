package model.adt;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final List<T> list;

    public MyList() {
        list = Collections.synchronizedList(new LinkedList<>());
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public T pop() {
        return list.remove(list.size()-1);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T elem : list) {
            stringBuilder.append(String.format("%s\n", elem.toString()));
        }
        return stringBuilder.toString();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }
}
