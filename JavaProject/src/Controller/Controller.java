package Controller;
import Model.PrgState;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Repo.Repo;
import Exception.*;

public class Controller {

    Repo repo;

    public Controller(Repo repo){
        this.repo = repo;
    }

    public void addProgram(PrgState newPrg) {
        repo.addPrg(newPrg);
    }

    public String oneStep(PrgState state, IStack<IStmt> copy_stack,String wholeProgram) throws Exception {
        var statement = copy_stack.pop();
        wholeProgram += "\n";
        wholeProgram += "\n";
        wholeProgram += "Execution Stack:\n";
        wholeProgram += statement.toString();
        wholeProgram += "\nSystem Table:\n";
        var table = state.getSymTable().getDictionary();
        for(var stat:table.keySet()){
            var st = "[" + stat + "," + table.get(stat) + "]"  + "\n";
            wholeProgram += st;
        }
        statement.execute(state);
        return wholeProgram;
    }

    public void allStep() throws Exception {
        String wholeProgram = "";
        var state = repo.getCurrentProgram();
        var stack = state.getStack();
        var table = state.getSymTable().getDictionary();
        var last = stack.pop();
        System.out.println("\n Execution Stack:");
        wholeProgram += "Execution Stack:\n";
        System.out.println(last);
        wholeProgram += last.toString();
        wholeProgram += "\n";
        for(var statement:table.keySet()){
            var st = "[" + statement + "," + table.get(statement) + "]"  + "\n";
            wholeProgram += st;
        }
        last.execute(state);
        var copy_stack = state.getStack();
        if(copy_stack.isEmpty())
            throw new StackException("stack is empty!");
        while(!copy_stack.isEmpty()){
            wholeProgram = oneStep(state,copy_stack,wholeProgram);
        }
        var output = state.getOutput();
        var result = "\n\n";
        for(var el:output){
            System.out.println("Output: " + el.toString());
            result += "Output:" + el;
        }
        wholeProgram += result;
        System.out.println(wholeProgram);
        repo.logPrgStateExec(wholeProgram);
    }
}
