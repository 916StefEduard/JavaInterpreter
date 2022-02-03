package model.statements;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import exceptions.UndefinedException;
import model.PrgState;
import model.adt.IDict;
import model.types.IType;
import model.types.IntType;
import model.values.IntValue;

public class AwaitStmt implements IStmt{
    private String var;

    public AwaitStmt(String var){
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!state.getSymTable().isDefined(var)){
            throw new UndefinedException("Variable is not present in the symbolTable\n");
        }
        IntValue foundIndex = (IntValue) state.getSymTable().lookup(var);
        if(!state.getLatchTable().contains(foundIndex.getValue()))
            return null;
        else
            state.getExeStack().push(this);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        var typeVar = typeEnv.lookup(var);
        if(typeVar.equals(new IntType())) {
            return typeEnv;
        }else{
            throw new TypeMismatchException("Variable is not of type int\n");
        }
    }

    @Override
    public String toString(){
        return "await(" + var + ")";
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
