package Repo;
import Model.PrgState;
import java.io.*;
import Exception.*;
import java.util.ArrayList;
import Exception.RepositoryException;
import Model.value.StringValue;

public class Repo implements IRepo {
    ArrayList<PrgState> myPrgStates;
    StringValue logFilePath;

    public Repo(StringValue logFilePath) throws IOException {
        myPrgStates = new ArrayList<PrgState>();
        this.logFilePath = logFilePath;
    }

    @Override
    public ArrayList<PrgState> getMyPrgStates(){
        return this.myPrgStates;
    }

    public String getOutput(){
        String output = "";
        for(var element:this.myPrgStates){
            output += element.getOutput();
            break;
        }
        return output;
    }

    @Override
    public void setPrgList(ArrayList<PrgState> newPrgStates){
        this.myPrgStates = newPrgStates;
    }

    public PrgState getPrgList() throws RepositoryException {
        if(myPrgStates.isEmpty())
            throw new RepositoryException("program state does not exist!");
        return this.myPrgStates.get(myPrgStates.size()-1);
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    public String SliceString(String path){
        StringBuilder normalPath = new StringBuilder();
        for(int i=53 ; i<path.length(); ++i){
            normalPath.append(path.charAt(i));
        }
        return normalPath.toString();
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws IOException, StackException {
        var logfile = new PrintWriter(new BufferedWriter(new FileWriter(String.valueOf(logFilePath),true)));
        String wholeProgram = "";
        var exeStack = prgState.getStack().top();
        if(exeStack != null)
            wholeProgram += "Execution Stack:" + exeStack.toString();
        wholeProgram += "\nSystem Table:";
        for(var element:prgState.getSymTable().getDictionary().keySet()){
            wholeProgram += "[" + element + "," + prgState.getSymTable().getDictionary().get(element) + "]";
        }
        wholeProgram += "\nFile Table:";
        if(prgState.getFiletable().getDictionary().isEmpty()) {
            wholeProgram += "[" + "]";
        }
        for(var element:prgState.getFiletable().getDictionary().keySet()){
            wholeProgram += "[" + this.SliceString(String.valueOf(element)) + "]";
        }
        wholeProgram += "\nHeap:";
        if(prgState.getHeap().getHashMap().isEmpty()) {
            wholeProgram += "[" + "]";
        }
        for(var element:prgState.getHeap().getHashMap().keySet()){
            wholeProgram += "[" + element.toString() + "->" + prgState.getHeap().getHashMap().get(element).toString() + "]";
        }
        wholeProgram += "\nId:" + prgState.getId() + "\n";
        logfile.write(wholeProgram);
        String output = "Output:";
        for(var element:this.myPrgStates){
            output += element.getOutput();
            break;
        }
        output += "\n";
        logfile.write(output);
        logfile.close();
    }

    @Override
    public String getFilePath() {
        return String.valueOf(this.logFilePath);
    }
}
