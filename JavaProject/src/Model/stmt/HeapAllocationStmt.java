package Model.stmt;
import Exception.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.RefType;

import java.util.Objects;

public class HeapAllocationStmt implements IStmt {
    String var_name;
    Exp exp;

    public HeapAllocationStmt(String var_name, Exp exp){
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public  PrgState execute(PrgState state) throws  Exception {
        var symTable = state.getSymTable();
        var heap = state.getHeap();
        if(!symTable.isDefined(var_name))
            throw new HeapException("Variable is not defined\n");
        var heapValue = symTable.lookup(var_name);
        var result = exp.eval(symTable,heap);
        heap.update(Integer.parseInt(String.valueOf(Character.getNumericValue(heapValue.toString().charAt(1)))),result);
        return null;
    }

    @Override
    public String toString(){
        return "Heap Writing:" + this.var_name + "," + this.exp;
    }

    @Override
    public IStmt deepcopy() {
        return new HeapAllocationStmt(var_name,exp);
    }

    @Override
    public String getStatement() {
        return "w";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        var typevar = typeEnv.lookup(var_name);
        var typexp = exp.typeCheck(typeEnv);
        if(Objects.equals(typevar.toString(), new RefType(typexp).toString())){
            return typeEnv;
        }else
            throw new MismatchException("Variables do not match\n");
    }
}
