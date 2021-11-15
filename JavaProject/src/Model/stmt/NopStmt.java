package Model.stmt;

import Model.PrgState;

public class NopStmt implements IStmt {

    public PrgState execute(PrgState state){
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new NopStmt();
    }

    @Override
    public String toString(){
        return "no operation";
    }
}
