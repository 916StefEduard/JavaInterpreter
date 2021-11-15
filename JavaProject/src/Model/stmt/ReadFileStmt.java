package Model.stmt;

import Model.PrgState;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;

import java.beans.Expression;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class ReadFileStmt implements IStmt{
    Exp exp;
    String var_name;

    public ReadFileStmt(Exp exp,String var_name){
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        var symTable = state.getSymTable().getDictionary();
        var table = state.getSymTable();
        IValue answer;
        String name;
        for(var element:symTable.keySet()){
            if(Objects.equals(element, var_name)){
                answer = symTable.get(element);
                name = element;
                break;
            }
        }
        var fileTable = state.getFiletable().getDictionary();
        var fileMap = state.getFiletable();
        StringValue remember = new StringValue("a");
        for(var file:fileTable.keySet()){
           if(file.toString() == exp.toString()){
               remember = file;
               break;
           }
        }
        var buffer = fileTable.get(remember);
        var value = buffer.readLine();
        int number = Integer.parseInt(value);
        table.update(var_name,new IntValue(number));
        return state;
    }

    public String toString(){
        String path = exp.toString();
        String normalPath = "";
        for(int i=52 ; i<path.length(); ++i){
            normalPath += path.charAt(i);
        }
        return "read data for variable:" + var_name + " from file:" + normalPath;
    }

    @Override
    public IStmt deepcopy() {
        return new ReadFileStmt(exp,var_name);
    }
}
