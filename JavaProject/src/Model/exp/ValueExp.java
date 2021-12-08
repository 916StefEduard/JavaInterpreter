package Model.exp;
import Exception.*;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class ValueExp implements Exp{
    IValue v;

    public ValueExp(IValue value){
        v = value;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws Exception {
        if(v == null)
            throw new InvalidOperandException("number does not exist!");
        return v;
    }

    @Override
    public String toString() {
        return  v.toString();
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        return v.getType();
    }
}
