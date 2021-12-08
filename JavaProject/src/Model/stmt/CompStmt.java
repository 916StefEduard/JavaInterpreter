package Model.stmt;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.types.IType;

public class CompStmt implements IStmt{
    IStmt first;
    IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    public IStmt getFirst(){
        return this.first;
    }

    public IStmt getSecond(){
        return this.second;
    }

    @Override
    public IStmt deepcopy() {
        return new CompStmt(first,second);
    }

    @Override
    public String getStatement() {
        return "compare(" + this.first + ")(" + this.second + ")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public String toString(){
        return first.toString() + ";" + second.toString();
    }

}
