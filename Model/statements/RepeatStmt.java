package model.statements;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

import java.beans.Expression;
import java.beans.SimpleBeanInfo;

public class RepeatStmt implements IStmt{
    IStmt stmt;
    IExp exp;

    public RepeatStmt(IStmt stmt,IExp exp){
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stack = state.getExeStack();
        IValue expValue = exp.eval(state.getSymTable(), state.getHeapTable());
        if (expValue.getType().equals(new BoolType())) {
            if (!((BoolValue)expValue).getVal()) {
                stack.push(this);
                stack.push(stmt);
            }
        } else {
            throw new TypeMismatchException("Condition of while statement not of type bool!");
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("repeat (%s){%s}", exp, stmt);
    }
}
