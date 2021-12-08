package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Exception.*;
import java.beans.Expression;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class ReadFileStmt implements IStmt{
    Exp exp;
    StringValue var_name;

    public ReadFileStmt(Exp exp,StringValue var_name){
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        var symTable = state.getSymTable().getDictionary();
        var table = state.getSymTable();
        boolean firstFound = false, secondFound = false;
        for(var element:symTable.keySet()){
            if(Objects.equals(element, var_name)){
                firstFound = true;
                break;
            }
        }
        if(firstFound){
            throw new FileException("Variable not found\n");
        }
        var fileTable = state.getFiletable().getDictionary();
        if(!Objects.equals(String.valueOf(exp), exp.toString())){
            throw new FileException("File name is not a string\n");
        }
        StringValue remember = new StringValue("a");
        for(var file:fileTable.keySet()){
           if(Objects.equals(file.toString(), exp.toString())){
               remember = file;
               secondFound = true;
               break;
           }
        }
        if(!secondFound){
            throw new FileException("File not found\n");
        }
        var buffer = fileTable.get(remember);
        var value = buffer.readLine();
        int number = Integer.parseInt(value);
        table.update(String.valueOf(var_name),new IntValue(number));
        return null;
    }

    public String toString(){
        String path = exp.toString();
        String normalPath = "";
        for(int i=53 ; i<path.length(); ++i){
            normalPath += path.charAt(i);
        }
        return "read data for variable:" + var_name + " from file:" + normalPath;
    }

    @Override
    public IStmt deepcopy() {
        return new ReadFileStmt(exp,var_name);
    }

    @Override
    public String getStatement() {
        return "readFile";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        var typeVar = typeEnv.lookup(String.valueOf(var_name));
        var typeExp = exp.typeCheck(typeEnv);
        return typeEnv;
    }
}
