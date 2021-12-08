package Model.stmt;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.MyStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Exception.*;
import Model.value.BoolValue;
import java.util.Objects;

public class WhileStmt implements IStmt{
    Exp exp;
    IStmt stmt;

    public WhileStmt(Exp exp,IStmt stmt){
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public String toString(){
        return "while(" + this.exp + "(" + this.stmt + "))";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        var stack = state.getStack();
        var initialSize = stack.getSize();
        this.stmt.execute(state);
        var updatedSize = stack.getSize() - initialSize;
        var newStack = new MyStack<IStmt>();
        while(newStack.getSize()!=updatedSize){
            newStack.push(stack.pop());
        }
        var value = exp.eval(state.getSymTable(),state.getHeap());
        newStack = newStack.reverse();
        while(!Objects.equals(value, new BoolValue(false))) {
            var copyStack = new MyStack<IStmt>();
            while (!newStack.isEmpty()) {
                var currentStmt = newStack.pop();
                currentStmt.execute(state);
                var StmtToString = state.toString(currentStmt);
                System.out.println(StmtToString);
                copyStack.push(currentStmt);
            }
            newStack = copyStack.reverse();
            value = exp.eval(state.getSymTable(),state.getHeap());
        }
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new WhileStmt(exp,stmt);
    }

    @Override
    public String getStatement() {
        return "while";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        var typeExp = exp.typeCheck(typeEnv);
        if(typeExp.equals(new BoolType())){
            return typeEnv;
        }else
            throw new StatementException("The condition of while has not the type bool");
    }
}
