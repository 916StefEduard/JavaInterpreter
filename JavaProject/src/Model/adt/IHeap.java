package Model.adt;
import Exception.*;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.HashMap;
import java.util.Map;

public interface IHeap<T1,T2>{
    public void add(T1 key,T2 value);
    public void remove(T1 key,T2 value) throws HeapException;
    public void update(T1 key, T2 new_value) throws HeapException;
    public HashMap<T1, T2> getHashMap();
    public T1 getLastKey() throws HeapException;
    public T2 getLastValue() throws HeapException;
    public boolean empty();
    public T2 lookup(T1 key);
    public int getSize();
    public boolean isDefined(IntValue key);
    public void createHeap(Map<T1,T2> hashMap);
    public void setContent(HashMap<T1,T2> hashMap);
}
