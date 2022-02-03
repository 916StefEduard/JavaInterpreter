package model.statements;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import model.PrgState;
import model.adt.IDict;
import model.expressions.IExp;
import model.types.BoolType;
import model.types.IType;

public class CondStmt implements  IStmt{
    String var;
    IExp e1,e2,e3;

    public CondStmt(String v,IExp e1,IExp e2,IExp e3){
        this.var = v;
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var stack = state.getExeStack();
        var newStmt = new IfStmt(e1,new AssignStmt(var,e2),new AssignStmt(var,e3));
        stack.push(newStmt);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeVar  = typeEnv.lookup(var);
        IType typeExp1 = e1.typeCheck(typeEnv);
        IType typeExp2 = e2.typeCheck(typeEnv);
        IType typeExp3 = e3.typeCheck(typeEnv);
        if(typeExp1.equals(new BoolType())){
            if(typeVar.equals(typeExp2) && typeVar.equals(typeExp3) && typeExp2.equals(typeExp3)){
                return typeEnv;
            }else{
                throw new TypeMismatchException("Variables do not match\n");
            }
        }else{
            throw new TypeMismatchException("E1 is not of type bool\n");
        }
    }
    @Override
    public String toString(){
        return "Cond(" + var + e1 + e2 + e3;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
