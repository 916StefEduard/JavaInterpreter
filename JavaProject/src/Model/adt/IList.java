package Model.adt;
import Exception.ListException;
public interface IList<T> {
    void add(T v);
    T pop() throws ListException;
    String toString();
    boolean empty();
    void clear();
}
