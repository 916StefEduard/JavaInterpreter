package Model.exp;
import Model.adt.IDict;
import Model.value.IValue;
import Model.value.StringValue;

public interface  Exp {

     IValue eval(IDict<String, IValue> symTable) throws Exception;
     String toString();
}
