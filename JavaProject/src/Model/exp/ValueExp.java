package Model.exp;
import Exception.*;
import Model.adt.IDict;
import Model.value.IValue;
import Model.value.StringValue;

public class ValueExp implements Exp{
    IValue v;

    public ValueExp(IValue value){
        v = value;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable) throws Exception {
        if(v == null)
            throw new InvalidOperandException("number does not exist!");
        return v;
    }

    @Override
    public String toString() {
        return  v.toString();
    }
}
