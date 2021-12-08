package Model.stmt;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import java.util.Objects;
import Exception.*;
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
        var heap = state.getHeap();
        var value = exp.eval(symTbl,heap);
        if(Objects.equals(value.toString(), "true")){
            this.thenS.execute(state);
        }else{
            this.elseS.execute(state);
        }
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new IfStmt(exp,thenS,elseS);
    }

    @Override
    public String getStatement() {
        return "if("+ exp.toString()+") then(" +thenS.toString()  +")else("+elseS.toString()+")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        var typExp = exp.typeCheck(typeEnv);
        if(typExp.equals(new BoolType())){
            thenS.typeCheck(typeEnv);
            elseS.typeCheck(typeEnv);
            return typeEnv;
        }else
            throw new StatementException("The condition of IF has not the type bool");
    }
}
