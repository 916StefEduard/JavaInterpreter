package Model.adt;
import Exception.ListException;
import java.util.ArrayList;

public class List<T> implements IList<T> {
    ArrayList<T> list;

    public List(){
        list = new ArrayList<T>();
    }

    @Override
    public void add(T v) {
        list.add(v);
    }



    @Override
    public T pop() throws ListException {
        if(this.list.isEmpty())
            throw new ListException("list is empty");
        return list.remove(list.size()-1);
    }

    public T getFirstElement() throws ListException {
        if(this.list.isEmpty())
            throw new ListException("list is empty!");
        return this.list.get(0);
    }

    public ArrayList<T> getElements(){
        return this.list;
    }

    @Override
    public boolean empty() {
        return this.list.isEmpty();
    }

    @Override
    public void clear(){
        this.list.clear();
    }

    @Override
    public boolean contains(T key) {
        return this.list.contains(key);
    }
}
