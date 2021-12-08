package Model.exp;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.RefValue;
import Exception.*;

import java.sql.Ref;

public class HeapReadingExp implements Exp{
    Exp exp;

    public HeapReadingExp(Exp exp){
        this.exp = exp;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws Exception {
         var result = exp.eval(symTable,heap);
         var heapAddress = heap.lookup(Character.getNumericValue(result.toString().charAt(1)));
        return heapAddress;
    }

    @Override
    public String toString(){
        return "Heap Reading:" + this.exp;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        var type = exp.typeCheck(typeEnv);
        if(type instanceof RefType){
            RefType  reft = (RefType) type;
            return reft.getInner();
        }else
            throw new HeapException("The rh argument is not of RefType\n");
    }
}
