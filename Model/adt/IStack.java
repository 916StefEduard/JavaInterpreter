package model.adt;

public interface IStack<T> {
    void push(T v);
    T pop();
    boolean isEmpty();
    String toString();
}
