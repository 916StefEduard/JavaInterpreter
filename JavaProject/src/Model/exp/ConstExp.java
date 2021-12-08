package Model.exp;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;
import Exception.*;
import Model.value.StringValue;

public class ConstExp implements Exp{
    int number;

    public ConstExp(int number){
        this.number = number;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws Exception {
        if(symTable.lookup(Integer.toString(number)) == null)
            throw new InvalidOperandException("number does not exist!");
        return symTable.lookup(Integer.toString(number));
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        return null;
    }

    public StringValue toStrings() {
        return new StringValue(Integer.toString(number));
    }
}
