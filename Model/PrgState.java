package model;

import exceptions.MyException;
import exceptions.PrgStateException;
import javafx.util.Pair;
import model.adt.*;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDict<String, IValue> symTable;
    private IDict<StringValue, BufferedReader> fileTable;
    private IHeap<IValue> heapTable;
    private IList<String> out;
    private IStmt originalProgram;
    private Integer id = 1;
    private ILockTable<Integer,Integer> lockTable;
    private ILatchTable<Integer,Integer> latchTable;
    private ISem semaphore;
    public static Integer lastID = 1;

    public PrgState(IStmt originalProgram) {
        exeStack = new MyStack<>();
        symTable = new MyDict<>();
        fileTable = new MyDict<>();
        heapTable = new MyHeap<>();
        lockTable = new LockTable<>();
        latchTable = new LatchTable<>();
        semaphore = new Sem();
        out = new MyList<>();
        this.originalProgram = originalProgram.deepCopy();
        exeStack.push(originalProgram);
    }

    public PrgState(IStack<IStmt> exeStack, IDict<String, IValue> symTable, IDict<StringValue, BufferedReader> fileTable, IHeap<IValue> heapTable,
                    IList<String> out, IStmt originalProgram,ILockTable<Integer,Integer> lockTable,ILatchTable<Integer,Integer> latchTable,ISem semaphore) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.heapTable = heapTable;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram.deepCopy();
        this.lockTable = lockTable;
        this.latchTable = latchTable;
        this.semaphore = semaphore;
        exeStack.push(originalProgram);
    }

    public PrgState(IStack<IStmt> exeStack, IDict<String, IValue> symTable, IDict<StringValue, BufferedReader> fileTable, IHeap<IValue> heapTable, IList<String> out
            ,ILockTable<Integer,Integer> lockTable,ILatchTable<Integer,Integer> latchTable,ISem semaphore) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.heapTable = heapTable;
        this.fileTable = fileTable;
        this.lockTable = lockTable;
        this.latchTable = latchTable;
        this.semaphore = semaphore;
    }

    public ISem getSemaphore(){
        return this.semaphore;
    }

    public ILatchTable<Integer,Integer> getLatchTable(){
        return this.latchTable;
    }

    public ILockTable<Integer,Integer> getLockTable(){
        return this.lockTable;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public int getId(){
        return this.id;
    }

    public PrgState oneStep() throws MyException {
        if (exeStack.isEmpty()) {
            throw new PrgStateException("Execution stack is empty!");
        }
        IStmt currentStmt = exeStack.pop();
        return currentStmt.execute(this);
    }

    public synchronized void setID() {
        lastID++;
        id = lastID;
    }

    public PrgState deepCopy() {
        return new PrgState(exeStack, symTable, fileTable, heapTable, out, originalProgram,lockTable,latchTable,semaphore);
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(IStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public IDict<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(IDict<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public IList<String> getOut() {
        return out;
    }

    public void setOut(IList<String> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public IDict<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IDict<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IHeap<IValue> getHeapTable() {
        return heapTable;
    }

    public void setHeapTable(IHeap<IValue> heapTable) {
        this.heapTable = heapTable;
    }

    @Override
    public String toString() {
        return String.format("ID: %s\nExecution stack:\n%s\nSymbol table:\n%s\nHeap table:\n%s\nOutput:\n%s\nFile table:\n%s\n------------------------", id, exeStack, symTable, heapTable, out, fileTable.keysToString());
    }
}
