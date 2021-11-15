package Model.stmt;


import Model.PrgState;
import Model.adt.List;
import Model.exp.Exp;
import Model.value.IValue;

public class PrintStmt implements IStmt{

    Exp expression;

    public PrintStmt(Exp exp){
        this.expression = exp;
    }

    public PrgState execute(PrgState state) throws Exception {
        var output = state.getOutput();
        output.add(expression.eval(state.getSymTable()));
        state.setOutput(output);
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new PrintStmt(expression);
    }

    @Override
    public String toString(){
        return "print(" + expression.toString() + ")";
    }
}
