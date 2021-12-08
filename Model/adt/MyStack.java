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
    public T top(){
        if(stack.size() != 0)
            return stack.get(stack.size()-1);
        else
            return null;
    }

    public MyStack<T> reverse() {
        var newStack = new MyStack<T>();
        var copyStack = this.stack;
        while(!copyStack.isEmpty()){
            newStack.push(copyStack.pop());
        }
        return newStack;
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int getSize() {
        return this.stack.size();
    }
}
