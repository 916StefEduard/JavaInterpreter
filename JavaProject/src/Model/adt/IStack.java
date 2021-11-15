package Model.adt;
import Exception.StackException;
public interface IStack<T> {

    T pop() throws StackException;
    void push(T v);
    boolean isEmpty();
    String toString();
}

