package Model.stmt;

import Model.PrgState;
import Model.exp.Exp;

import java.util.Objects;

public class IfStmt implements  IStmt{
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el){
        this.exp = e;
        this.thenS = t;
        this.elseS = el;
    }

    @Override
    public String toString(){
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString()  +")ELSE("+elseS.toString()+"))";
    }

    public PrgState execute(PrgState state) throws Exception {
        var symTbl = state.getSymTable();
        var value = exp.eval(symTbl);
        if(Objects.equals(value.toString(), "true")){
            this.thenS.execute(state);
        }else{
            this.elseS.execute(state);
        }
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new IfStmt(exp,thenS,elseS);
    }
}
