package model.expressions;

import exceptions.MyException;
import model.adt.IDict;
import model.adt.IHeap;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class MulExp implements  IExp{
    IExp e1,e2;

    public MulExp(IExp e1,IExp e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws MyException {
        IValue value1 = e1.eval(symTable,heapTable);
        if(value1.getType().equals(new IntType())){
            IValue value2 = e2.eval(symTable,heapTable);
            IntValue int1 = (IntValue) value1;
            if(value2.getType().equals(new IntType())){
                IntValue int2 = (IntValue) value2;
                return new IntValue(int1.getValue()*int2.getValue() - (int1.getValue()+int2.getValue()));
                    }
                }
        return null;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return null;
    }
    @Override
    public String toString(){
        return "Mul(" + this.e1 + " " + this.e2 + ")";
    }

    @Override
    public IExp deepCopy() {
        return null;
    }
}
