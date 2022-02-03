package model.statements;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import exceptions.UndefinedException;
import model.PrgState;
import model.adt.IDict;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class CountDown implements IStmt{
    private String var;

    public CountDown(String var){
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(state.getSymTable().lookup(this.var) != null){
            IValue index =  state.getSymTable().lookup(this.var);
            if(index.getType().equals(new IntType())) {
                IntValue intValue = (IntValue) index;
                synchronized (state.getLatchTable()) {
                    if (state.getLatchTable().get(intValue.getValue()) == null) {
                        return null;
                    }
                    int numberOfCount = state.getLatchTable().get(intValue.getValue());
                    if (numberOfCount > 0) {
                        state.getLatchTable().update(intValue.getValue(), numberOfCount - 1);
                        state.getOut().add(String.valueOf(state.getId()));
                    }
                }
            }else{
                throw new TypeMismatchException("Variable is not of type int\n");
            }
        }else{
            throw new UndefinedException("Variable is not present in the symbolTable\n");
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        var typeVar = typeEnv.lookup(var);
        if(typeVar.equals(new IntType())) {
            return typeEnv;
        }else{
            throw new TypeMismatchException("TypeVar is not of type int\n");
        }
    }

    @Override
    public String toString(){
        return "countDown(" + this.var + ")";
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
