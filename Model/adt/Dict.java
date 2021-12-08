package Model.adt;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import Exception.DictionaryException;

public class Dict<T1,T2> implements IDict<T1,T2> {
    HashMap<T1, T2> dictionary;

    public Dict() {
        dictionary = new HashMap<T1,T2>();
    }

    public Map<T1, T2> getDictionary(){
        return this.dictionary;
    }

    public Vector<T1> getKeys(){
        var keys = new Vector<T1>();
        for(var element:dictionary.keySet()){
            keys.add(element);
        }
        return keys;
    }

    @Override
    public void add(T1 v1, T2 v2) {
        dictionary.put(v1,v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        dictionary.put(v1,v2);
    }

    @Override
    public void remove(T1 v1) {
        dictionary.remove(v1);
    }

    @Override
    public T2 lookup(T1 id) throws DictionaryException {
        for(var element:dictionary.keySet()){
            if(element == id) {
                return dictionary.get(element);
            }
        }
        throw new DictionaryException("id is not defined");
    }

    @Override
    public boolean isDefined(String id) throws DictionaryException {
        boolean ok = false;
        for(var element:dictionary.keySet()){
            if (element == id) {
                ok = true;
                break;
            }
        }
        if(!ok){
            throw new DictionaryException("id is not defined");
        }
        return ok;
    }
}
