package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class LockStmt implements IStmt {
    private String var;

    public LockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(var)) {
            return null;
        }
        IValue index = state.getSymTable().lookup(var);
        if (!index.getType().equals(new IntType())) {
           return null;
        }
        int indexValue = ((IntValue) index).getValue();
        synchronized (state.getLockTable()) {
            if (!state.getLockTable().contains(indexValue)) {
                return null;
            }
            if (state.getLockTable().get(indexValue) == -1) {
                state.getLockTable().update(indexValue, state.getId());
            } else {
                state.getExeStack().push(this);
            }
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new LockStmt(var);
    }

    @Override
    public String toString() {
        return String.format("lock(%s)", var);
    }
}