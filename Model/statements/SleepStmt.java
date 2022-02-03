package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.types.IType;

public class SleepStmt implements IStmt{
    int number;

    public SleepStmt(int number){
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var stack = state.getExeStack();
        if(number != 0){
            int newNumber = number - 1;
            var newStmt = new SleepStmt(newNumber);
            stack.push(newStmt);
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
    @Override
    public String toString(){
        return "sleep" + String.valueOf(number);
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
