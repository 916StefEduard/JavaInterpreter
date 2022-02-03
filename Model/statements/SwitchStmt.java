package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.expressions.IExp;
import model.expressions.RelationalExp;
import model.types.IType;

public class SwitchStmt implements IStmt{
    IExp e1,e2,e3;
    IStmt s1,s2,s3;

    public SwitchStmt(IExp e1,IExp e2,IExp e3,IStmt s1,IStmt s2,IStmt s3){
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var stack = state.getExeStack();
        var newStmt = new IfStmt(new RelationalExp(e1,e2,"=="),s1,
                new IfStmt(new RelationalExp(e1,e3,"=="),s2,s3));
        stack.push(newStmt);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "Switch(" + this.e1 + "," + this.e2 + "," + this.e3 + ")(" + this.s1 + "," +
                this.s2 + "," + this.s3 + ")";
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
