package Model.adt;
import Exception.*;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.HashMap;
import java.util.Map;

public class Heap<T1,T2> implements IHeap<T1,T2>{
    HashMap<T1,T2> hashMap;

    public Heap(){
        hashMap = new HashMap<T1,T2>();
    }

    @Override
    public void add(T1 key,T2 value){
        hashMap.put(key,value);
    }

    @Override
    public void remove(T1 key,T2 value) throws HeapException {
        if(this.hashMap.size()==0){
            throw new HeapException("heap is empty!\n");
        }
        this.hashMap.remove(key,value);
    }

    @Override
    public void update(T1 key, T2 new_value) throws HeapException {
        boolean found = false;
        for(var element:this.hashMap.keySet()){
            if(element == key){
                this.hashMap.replace(key,this.hashMap.get(key),new_value);
                found = true;
            }
        }
        if(!found)
            throw new HeapException("Element is not found\n");
    }

    @Override
    public HashMap<T1, T2> getHashMap() {
        return this.hashMap;
    }


    @Override
    public T1 getLastKey() throws HeapException {
        if(this.hashMap.isEmpty()){
            throw new HeapException("heap is empty\n");
        }
        T1 key = null;
        for(var element:this.hashMap.keySet()){
            key = element;
        }
        return key;
    }

    @Override
    public T2 getLastValue() throws HeapException {
        if(this.hashMap.isEmpty()){
            throw new HeapException("heap is empty\n");
        }
        T2 value = null;
        for(var element:this.hashMap.keySet()){
            value = this.hashMap.get(element);
        }
        return value;
    }

    @Override
    public T2 lookup(T1 key){
        T2 value = null;
        for(var element:this.hashMap.keySet()){
            if(element.equals(key)){
                value = this.hashMap.get(element);
            }
        }
        return value;
//        throw new HeapException("Key is not found\n");
    }

    @Override
    public int getSize(){
        return this.hashMap.size();
    }

    @Override
    public boolean isDefined(IntValue key) {
        boolean ok = false;
        for(var element:this.hashMap.keySet()){
            if(element == key)
                ok = true;
        }
        return ok;
    }

    @Override
    public boolean empty(){
        return this.hashMap.isEmpty();
    }

    @Override
    public void setContent(HashMap<T1,T2> hashMap){
        this.hashMap = hashMap;
    }

    @Override
    public  void createHeap(Map<T1,T2> hashMap){
        this.hashMap = (HashMap<T1, T2>) hashMap;
    }
}
