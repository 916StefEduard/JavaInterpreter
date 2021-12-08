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
import java.io.FileReader;
import java.util.Objects;

public class OpenFileStmt implements IStmt{
    Exp exp;

    public OpenFileStmt(Exp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        var fileTable = state.getFiletable().getDictionary();
        var fileMap = state.getFiletable();
        if(!Objects.equals(String.valueOf(exp), exp.toString())){
            throw new FileException("File name is not a string\n");
        }
        for(var element:fileTable.keySet()){
            if(element == exp){
                throw new FileException("duplicate file");
            }
        }
        BufferedReader reader = new BufferedReader(new FileReader(exp.toString()));
        fileMap.add(new StringValue(exp.toString()),reader);
        return null;
    }

    public String toString(){
        String path = exp.toString();
        String normalPath = "";
        for(int i=53 ; i<path.length(); ++i){
            normalPath += path.charAt(i);
        }
        return "open file:" + normalPath;
    }

    @Override
    public IStmt deepcopy() {
        return new OpenFileStmt(exp);
    }

    @Override
    public String getStatement() {
        return "open";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        var value = (VarExp) exp;
        if(!Objects.equals(value.getId(), exp.toString())){
            throw new MismatchException("File name is not a string\n");
        }
        typeEnv.add(exp.toString(),new StringType());
        return typeEnv;
    }
}
