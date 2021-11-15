package Model.stmt;
import Exception.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;
import Model.value.StringValue;

public class AssignStmt implements IStmt{

    String id;
    Exp expression;

    public AssignStmt(String id, Exp exp){
        this.id = id;
        this.expression = exp;
    }

    @Override
    public String toString(){
        return this.id + "=" + this.expression.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        var stack = state.getStack();
        if(stack.isEmpty())
            throw new StackException("stack is empty!");
        if(symTbl.isDefined(id)){
            IValue value = expression.eval(symTbl);
            IType typeId = (symTbl.lookup(id)).getType();
            if(value.getType().equals(typeId)){
                symTbl.update(id,value);
            }else{
                throw new InputException("declared type of variable "+id+" and type of  the assigned expression do not match");
            }
        }else{
            throw new InvalidOperandException("the used variable" +id + " was not declared before");
        }
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new AssignStmt(id,expression);
    }
}
