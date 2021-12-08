package Model.exp;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;
import Exception.DictionaryException;

public class VarExp implements Exp{
    String id;

    public VarExp(String id){
        this.id = id;
    }

    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws DictionaryException {
        return symTable.lookup(id);
    }

    public String toString() {
        return id;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        return typeEnv.lookup(id);
    }


}
