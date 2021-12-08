package Model.stmt;
import Exception.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.RefValue;
import java.util.Objects;

public class NewStmt implements IStmt{
    String var_name;
    Exp exp;

    public NewStmt(String var_name, Exp exp){
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        var symTable = state.getSymTable();
        var heap = state.getHeap();
        if(!symTable.isDefined(var_name))
            throw new HeapException("Variable is not defined\n");
        var refValue = symTable.lookup(var_name);
        if(!Objects.equals(refValue.getType(), new RefValue().getType()))
            throw new HeapException("Variable is not of ref type\n");
        var result = exp.eval(symTable,heap);
        var newAddress = new IntValue(1);
        if(heap.getSize() != 0)
            newAddress = new IntValue(heap.getSize() + 1);
        var copyValue = (RefValue) refValue;
        heap.add(Integer.parseInt(String.valueOf(newAddress)),result);
        var newRefValue = new RefValue(newAddress,copyValue.getLocationType());
        symTable.update(var_name,newRefValue);
        return null;
    }

    @Override
    public String toString(){
        return "Allocation:" + this.var_name;
    }

    @Override
    public IStmt deepcopy() {
        return new NewStmt(var_name,exp);
    }

    @Override
    public String getStatement() {
        return "new";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        var typevar = typeEnv.lookup(var_name);
        var typexp = exp.typeCheck(typeEnv);
        if(Objects.equals(typevar.toString(), new RefType(typexp).toString()))
            return typeEnv;
        else
            throw new StatementException("NEW stmt: right hand side and left hand side have different types ");
    }
}
