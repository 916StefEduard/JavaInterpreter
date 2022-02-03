package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.expressions.IExp;
import model.expressions.RelationalExp;
import model.expressions.VarExp;
import model.types.IType;
import model.types.IntType;

public class ForStmt implements IStmt{
    IExp e1,e2,e3;
    IStmt stmt;

    public ForStmt(IExp e1,IExp e2,IExp e3,IStmt stmt){
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var stack = state.getExeStack();
        var newStmt = new CompStmt(new VarDecStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",e1),new WhileStmt(new RelationalExp(new VarExp("v"),e2,"<"),
                        new CompStmt(stmt,new AssignStmt("v",e3)))));
        stack.push(newStmt);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "for(" + e1 + ";" + e2 + ";" + e3 + ")";
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
