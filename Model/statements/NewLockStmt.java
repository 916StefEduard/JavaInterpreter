package model.statements;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import exceptions.UndefinedException;
import model.PrgState;
import model.adt.IDict;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IntValue;

public class NewLockStmt implements IStmt {
    private String var;
    private int nextFree = 0;

    public NewLockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        synchronized (state.getLockTable()) {
            state.getLockTable().put(nextFree, -1);
        }
        if (state.getSymTable().isDefined(var)) {
            if (!state.getSymTable().lookup(var).getType().equals(new IntType())) {
                throw new TypeMismatchException(String.format("%s not of integer type", var));
            }
            state.getSymTable().update(var, new IntValue(nextFree));
        } else {
            throw new UndefinedException(String.format("%s does not exist in the symbol table", var));
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.lookup(var);
        if (!varType.equals(new IntType())) {
            throw new TypeMismatchException(String.format("%s not of integer type!", var));
        }
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLockStmt(var);
    }

    @Override
    public String toString() {
        return String.format("newLock(%s)", var);
    }
}
