package Model.stmt;
import Exception.*;
import Model.PrgState;
import Model.adt.List;
import Model.types.IType;
import Model.value.IValue;

import java.util.Objects;

public class VarDeclStmt implements IStmt{
    String name;
    IType type;

    public VarDeclStmt(String name, IType type){
        this.name = name;
        this.type = type;
    }

    public PrgState execute(PrgState state) throws StatementException, InputException {
        var output = state.getSymTable();
        var stack = state.getStack();
        if(stack.isEmpty())
            throw new StatementException("stack is empty");
        if(!Objects.equals(this.type.getType(), "int") && !Objects.equals(this.type.getType(), "bool")){
            throw new InputException("invalid type");
        }
        output.add(this.name,this.type.defaultValue());
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new VarDeclStmt(name,type);
    }

    public String toString(){
        return "variable declaration name:" + this.name + " and type: " + this.type;
    }
}
