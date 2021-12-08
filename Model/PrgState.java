package Model;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.StringValue;
import java.io.BufferedReader;
import java.util.*;
import Exception.*;

public class PrgState {

    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IDict<StringValue, BufferedReader> fileTable;
    IHeap<Integer,IValue> heap;
    ArrayList<IValue> out;
    IStmt originalProgram;
    int id;

    public PrgState(IStack<IStmt> stack, IDict<String, IValue> symTable, ArrayList<IValue> out,IDict<StringValue,BufferedReader> filetable, IHeap<Integer,IValue> heap,IStmt program,int id){
        this.exeStack = stack;
        this.symTable= symTable;
        this.out = out;
        this.originalProgram = program.deepcopy();
        this.fileTable = filetable;
        this.heap = heap;
        this.id = id;
        stack.push(program);
    }

    public IStmt getOriginalProgram(){
        return this.originalProgram;
    }

    public ArrayList<IValue> getOutput() {
        return this.out;
    }

    public IDict<String, IValue> getSymTable() {
        return this.symTable;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public IDict<String,IValue> getSymTableDeepCopy(){
        Dict<String,IValue> dict = new Dict<String,IValue>();
        for(var el:this.symTable.getDictionary().keySet()){
            dict.add(el,symTable.getDictionary().get(el));
        }
        return dict;
    }

    public IDict<StringValue,BufferedReader> getFiletable(){
        return this.fileTable;
    }

    public IStack<IStmt> getStack(){
        return this.exeStack;
    }

    public IHeap<Integer,IValue>getHeap(){
        return this.heap;
    }

    public boolean isNotCompleted(){
        return !this.exeStack.isEmpty();
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

    public void setFileTable(IDict<StringValue,BufferedReader> fileTable){
        this.fileTable = fileTable;
    }

    public void setHeap(Map<Integer,IValue> hashMap){
         this.heap.createHeap(hashMap);
    }

    public  int getId(){
        return id;
    }

    public PrgState getPrgState(){
        var newStack = new MyStack<IStmt>();
        return new PrgState(newStack,this.symTable,this.out,this.fileTable,this.heap,this.originalProgram,this.id);
    }

    public String SliceString(String path){
        StringBuilder normalPath = new StringBuilder();
        for(int i=53 ; i<path.length(); ++i){
            normalPath.append(path.charAt(i));
        }
        return normalPath.toString();
    }

    public String toString(IStmt currentStmt){
        String wholeProgram = "";
        wholeProgram += "Id:" + id + "\n";
        wholeProgram += "Execution Stack:" + currentStmt.toString();
        wholeProgram += "\nSystem Table:";
        for(var element:this.symTable.getDictionary().keySet()){
            wholeProgram += "[" + element + "," + this.symTable.getDictionary().get(element) + "]";
        }
        wholeProgram += "\nFile Table:";
        if(this.fileTable.getDictionary().isEmpty()) {
            wholeProgram += "[" + "]";
        }
        for(var element:fileTable.getDictionary().keySet()){
            wholeProgram += "[" + this.SliceString(String.valueOf(element)) + "]";
        }
        wholeProgram += "\nHeap:";
        if(this.heap.getHashMap().isEmpty()) {
            wholeProgram += "[" + "]";
        }
        for(var element:heap.getHashMap().keySet()){
            wholeProgram += "[" + element.toString() + "->" + heap.getHashMap().get(element).toString() + "]";
        }
        wholeProgram += "\n";
        return wholeProgram;
    }

    public PrgState oneStep() throws Exception {
        if(exeStack.isEmpty())
            throw new Exception("PrgState stack is empty\n");
        var currentStmt = exeStack.pop();
        System.out.println(this.toString(currentStmt));
        return currentStmt.execute(this);
    }
}