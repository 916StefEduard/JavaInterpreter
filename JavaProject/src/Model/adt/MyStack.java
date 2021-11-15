package Model.adt;
import Exception.StackException;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    Stack<T> stack;

    public MyStack(){
        stack = new Stack<T>();
    }

    @Override
    public T pop() throws StackException {
        if(stack.isEmpty()){
            throw new StackException("stack is empty");
        }
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
