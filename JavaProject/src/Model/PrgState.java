package Model;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.IStack;
import Model.adt.List;
import Model.stmt.IStmt;
import Model.types.IType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Vector;

public class PrgState {

    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IDict<StringValue, BufferedReader> filetable;
    ArrayList<IValue> out;
    IStmt originalProgram; //optional field, but good to have

    public PrgState(IStack<IStmt> stack, IDict<String, IValue> symTable, ArrayList<IValue> out,IDict<StringValue,BufferedReader> filetable,IStmt program){
        this.exeStack = stack;
        this.symTable= symTable;
        this.out = out;
        this.originalProgram = program.deepcopy();
        this.filetable = filetable;
        stack.push(program);
    }

    public Vector<IValue> getKeys(){
        return this.getKeys();
    }

    public ArrayList<IValue> getOutput() {
        return this.out;
    }

    public IDict<String, IValue> getSymTable() {
        return this.symTable;
    }

    public IDict<StringValue,BufferedReader> getFiletable(){
        return this.filetable;
    }

    public IStack<IStmt> getStack(){
        return this.exeStack;
    }

    public void setOutput(ArrayList<IValue> output) {
        this.out = output;
    }

    public void setSymTable(IDict<String,IValue> symTable){
        this.symTable = symTable;
    }

    public void setExeStack(IStack<IStmt> stack){
        this.exeStack = stack;
    }
}