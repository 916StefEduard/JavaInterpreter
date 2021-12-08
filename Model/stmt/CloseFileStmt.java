package Model.stmt;
import Exception.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.exp.VarExp;
import Model.types.IType;
import Model.types.StringType;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Objects;

public class CloseFileStmt implements IStmt{
    Exp exp;

    public CloseFileStmt(Exp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        var fileTable = state.getFiletable();
        var fileMaps = state.getFiletable().getDictionary();
        StringValue remember = new StringValue("a");
        boolean found = false;
        for(var file:fileMaps.keySet()){
            if(Objects.equals(file.toString(), exp.toString())){
                remember = file;
                found = true;
                break;
            }
        }
        if(!Objects.equals(String.valueOf(exp), exp.toString())){
            throw new FileException("File name is not a string\n");
        }
        if(!found){
            throw new FileException("File does not exist\n");
        }
        var buffer = fileMaps.get(remember);
        buffer.readLine();
        fileTable.remove(remember);
        return null;
    }

    public String toString(){
        String path = exp.toString();
        StringBuilder normalPath = new StringBuilder();
        for(int i=53 ; i<path.length(); ++i){
            normalPath.append(path.charAt(i));
        }
        return "closed file:" + normalPath;
    }

    @Override
    public IStmt deepcopy() {
        return new CloseFileStmt(exp);
    }

    @Override
    public String getStatement() {
        return "closefile";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        var value = (VarExp) exp;
        if(!Objects.equals(value.getId(), exp.toString())){
            throw new MismatchException("File name is not a string\n");
        }
        typeEnv.remove(exp.toString());
        return typeEnv;
    }
}
