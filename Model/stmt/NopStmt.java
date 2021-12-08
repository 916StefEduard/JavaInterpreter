package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;

public class NopStmt implements IStmt {
    Exp exp;

    public PrgState execute(PrgState state){
        return null;
    }
    public NopStmt(Exp exp){
        this.exp = exp;
    }

    @Override
    public IStmt deepcopy() {
        return new NopStmt(exp);
    }

    @Override
    public String getStatement() {
        return "nop";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        return null;
    }

    @Override
    public String toString(){
        return "no operation";
    }
}
