package model.statements;

import exceptions.MyException;
import javafx.util.Pair;
import model.PrgState;
import model.adt.IDict;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.List;

public class ReleaseStmt implements IStmt{
    private String var;

    public ReleaseStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (state.getSymTable().isDefined(var)) {
            IValue index = state.getSymTable().lookup(var);
            if (index.getType().equals(new IntType())) {
                Integer foundIndex = ((IntValue) index).getValue();
                synchronized (state.getSemaphore()) {
                    if (state.getSemaphore().isDefined(foundIndex)) {
                        Pair<Integer, List<Integer>> value = state.getSemaphore().lookup(foundIndex);
                        if (value.getValue().contains(state.getId()))
                            value.getValue().remove((Integer) state.getId());
                    }
                }
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
        return null;
    }

    @Override
    public String toString() {
        return "Release(" + var + ")";
    }
}