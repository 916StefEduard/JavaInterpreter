package Model.stmt;

import Model.PrgState;
import Model.exp.Exp;
import Model.types.StringType;
import Model.value.StringValue;
import Exception.StackException;
import java.beans.Expression;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class OpenFileStmt implements IStmt{
    Exp exp;

    public OpenFileStmt(Exp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        var table = state.getSymTable();
        var fileTable = state.getFiletable().getDictionary();
        var fileMap = state.getFiletable();
        for(var element:fileTable.keySet()){
            if(element == exp){
                throw new Exception("duplicate file");
            }
        }
        BufferedReader reader = new BufferedReader(new FileReader(exp.toString()));
        fileMap.add(new StringValue(exp.toString()),reader);
        return state;
    }

    public String toString(){
        String path = exp.toString();
        String normalPath = "";
        for(int i=52 ; i<path.length(); ++i){
            normalPath += path.charAt(i);
        }
        return "open file:" + normalPath;
    }

    @Override
    public IStmt deepcopy() {
        return new OpenFileStmt(exp);
    }
}
