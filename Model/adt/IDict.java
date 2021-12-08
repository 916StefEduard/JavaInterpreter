package Model.adt;
import Exception.DictionaryException;
import java.util.Map;

public interface IDict<T1,T2>{

    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2);
    void remove(T1 v1);
    T2 lookup(T1 id) throws DictionaryException;
    boolean isDefined(String id) throws DictionaryException;
    String toString();
    Map<T1, T2> getDictionary();
}
