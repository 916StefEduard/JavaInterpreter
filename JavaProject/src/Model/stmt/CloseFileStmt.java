package Model.stmt;

import Model.PrgState;
import Model.exp.Exp;
import Model.types.StringType;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.StringReader;

public class CloseFileStmt implements IStmt{
    Exp exp;

    public CloseFileStmt(Exp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        var symTable = state.getSymTable();
        var fileTable = state.getFiletable();
        var fileMaps = state.getFiletable().getDictionary();
        StringValue word = new StringValue();
        StringValue remember = new StringValue("a");
        for(var file:fileMaps.keySet()){
            if(file.toString() == exp.toString()){
                remember = file;
                break;
            }
        }
        var buffer = fileMaps.get(remember);
        buffer.readLine();
        fileTable.remove(remember);
        return state;
    }

    public String toString(){
        String path = exp.toString();
        String normalPath = "";
        for(int i=52 ; i<path.length(); ++i){
            normalPath += path.charAt(i);
        }
        return "closed file:" + normalPath;
    }

    @Override
    public IStmt deepcopy() {
        return new CloseFileStmt(exp);
    }
}
