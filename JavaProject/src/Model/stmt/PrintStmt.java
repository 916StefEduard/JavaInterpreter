package Model.stmt;


import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;

public class PrintStmt implements IStmt{

    Exp expression;

    public PrintStmt(Exp exp){
        this.expression = exp;
    }

    public PrgState execute(PrgState state) throws Exception {
        var output = state.getOutput();
        var heap = state.getHeap();
        output.add(expression.eval(state.getSymTable(),heap));
        state.setOutput(output);
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new PrintStmt(expression);
    }

    @Override
    public String getStatement() {
        return "print";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        return "print(" + expression.toString() + ")";
    }
}
