package Model.stmt;
import Exception.*;
import Model.PrgState;
import Model.adt.IStack;
import Model.adt.MyStack;

public class CompStmt implements IStmt{
    IStmt first;
    IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }


    @Override
    public PrgState execute(PrgState state) throws StatementException {
        IStack<IStmt> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        var symTable = state.getSymTable();
        var keys = symTable.getDictionary();
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new CompStmt(first,second);
    }

    @Override
    public String toString(){
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

}
