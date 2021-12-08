package Model.stmt;
import Model.PrgState;
import Model.adt.*;
import Model.types.IType;
import Model.value.IValue;
import Model.value.StringValue;

import javax.swing.text.Style;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Stack;

public class ForkStmt implements IStmt{
    IStmt stmt;

    public ForkStmt(IStmt stmt){
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        var newStack = new MyStack<IStmt>();
        var newPrgState = new PrgState(newStack,state.getSymTableDeepCopy(),state.getOutput(),state.getFiletable(),state.getHeap(),this.stmt,state.getId()+1);
        return newPrgState;
    }

    @Override
    public IStmt deepcopy() {
        return null;
    }

    @Override
    public String getStatement() {
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        stmt.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        return "Fork(" + this.stmt + ")";
    }
}
