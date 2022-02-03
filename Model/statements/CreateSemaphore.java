package model.statements;

import exceptions.MyException;
import javafx.util.Pair;
import model.PrgState;
import model.adt.IDict;
import model.expressions.IExp;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;

public class CreateSemaphore implements IStmt {

    private String var;
    private IExp exp1;

    public CreateSemaphore(String var, IExp exp1) {
        this.var = var;
        this.exp1 = exp1;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue expValue = exp1.eval(state.getSymTable(), state.getHeapTable());
        if (expValue.getType().equals(new IntType())) {
            Integer number = ((IntValue) expValue).getValue();
            synchronized (state.getSemaphore()) {
                if (state.getSymTable().isDefined(var)) {
                    if (state.getSymTable().lookup(var).getType().equals(new IntType())) {
                        Integer freeLocation = state.getSemaphore().getFreeLocation();
                        state.getSemaphore().add(freeLocation, new Pair<>(number, new ArrayList<>()));
                        state.getSymTable().update(var, new IntValue(freeLocation));
                    }
                }
            }
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType type = typeEnv.lookup(var);

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
    @Override
    public String toString() {
        return "CreateSemaphore(" + var + "," + exp1 + ")";
    }
}