package Model.stmt;
import Exception.*;
import Model.PrgState;
import Model.adt.IDict;
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
        output.add(this.name,this.type.defaultValue());
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new VarDeclStmt(name,type);
    }

    @Override
    public String getStatement() {
        return "vardeclaration";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        typeEnv.add(name,type);
        return typeEnv;
    }

    public String toString(){
        return "variable declaration:" + this.name + ",type:" + this.type;
    }
}
