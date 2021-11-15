package Model.exp;
import Model.adt.IDict;
import Model.value.IValue;
import Exception.DictionaryException;
import Model.value.StringValue;

public class VarExp implements Exp{
    String id;

    public VarExp(String id){
        this.id = id;
    }

    public IValue eval(IDict<String, IValue> symTable) throws DictionaryException {
        return symTable.lookup(id);
    }

    public String toString() {
        return id;
    }

}
