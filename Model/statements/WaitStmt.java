package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.expressions.IExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.types.IType;
import model.values.IntValue;

public class WaitStmt implements  IStmt{
    IntValue number;

    public WaitStmt(IntValue number){
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var stack = state.getExeStack();
        int copy = number.getValue();
        if(copy != 0){
            stack.push(new CompStmt(new PrintStmt(new ValueExp(number)),new WaitStmt(new IntValue(number.getValue() - 1))));
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "Wait(" + number.getValue() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
