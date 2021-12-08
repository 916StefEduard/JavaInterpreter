package Model.stmt;
import Exception.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;

public class AssignStmt implements IStmt{

    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp){
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString(){
        return this.id + "=" + this.exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        var symTbl = state.getSymTable();
        var stack = state.getStack();
        if(stack.isEmpty())
            throw new StackException("stack is empty!");
        if(symTbl.isDefined(id)){
            var heap = state.getHeap();
            var value = exp.eval(symTbl,heap);
            var typeId = (symTbl.lookup(id)).getType();
            if(value.getType().equals(typeId)){
                symTbl.update(id,value);
            }else{
                throw new InputException("declared type of variable "+id+" and type of  the assigned exp do not match");
            }
        }else{
            throw new InvalidOperandException("the used variable" +id + " was not declared before");
        }
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new AssignStmt(id,exp);
    }

    @Override
    public String getStatement() {
        return "assign(" + this.id + ")(" + this.exp.toString() + ")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        var typeVar = typeEnv.lookup(id);
        var typeExp = exp.typeCheck(typeEnv);
        if(typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new StatementException("Assignment: right hand side and left hand side have different types ");
    }

}
