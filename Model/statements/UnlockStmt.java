package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class UnlockStmt implements IStmt {
    private String var;

    public UnlockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(var))
            return null;
        IValue index = state.getSymTable().lookup(var);
        if (!index.getType().equals(new IntType())) {
            return null;
        }
        int indexValue = ((IntValue) index).getValue();
        synchronized (state.getLockTable()) {
            if (!state.getLockTable().contains(indexValue)) {
                return null;
            }
            if (state.getLockTable().get(indexValue) == state.getId()) {
                state.getLockTable().update(indexValue, -1);
            }
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.lookup(var);
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("unlock(%s)", var);
    }

    @Override
    public IStmt deepCopy() {
        return new UnlockStmt(var);
    }
}