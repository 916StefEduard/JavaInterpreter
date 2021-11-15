package Model.exp;
import Model.adt.IDict;
import Model.value.IValue;
import Exception.*;
import Model.value.StringValue;

public class ConstExp implements Exp{
    int number;

    public ConstExp(int number){
        this.number = number;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable) throws Exception {
        if(symTable.lookup(Integer.toString(number)) == null)
            throw new InvalidOperandException("number does not exist!");
        return symTable.lookup(Integer.toString(number));
    }

    public StringValue toStrings() {
        return new StringValue(Integer.toString(number));
    }
}
