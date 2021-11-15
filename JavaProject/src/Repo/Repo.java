package Repo;
import Model.PrgState;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import Exception.RepositoryException;
import java.util.ArrayList;

public class Repo implements IRepo {
    ArrayList<PrgState> myPrgStates;
    String logFilePath;

    public Repo(String logFilePath) {
        myPrgStates = new ArrayList<PrgState>();
        this.logFilePath = logFilePath;
    }

    public ArrayList<PrgState> getMyPrgStates(){
        return this.myPrgStates;
    }

    public PrgState getCurrentProgram() throws RepositoryException {
        if(myPrgStates.isEmpty())
            throw new RepositoryException("program state does not exist!");
        return this.myPrgStates.get(myPrgStates.size()-1);
    }

    @Override
    public PrgState getCrtPrg() throws RepositoryException {
        if(myPrgStates.isEmpty())
            throw new RepositoryException("program state does not exist!");
        return myPrgStates.remove(myPrgStates.size()-1);
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    @Override
    public void logPrgStateExec(String word) throws IOException {
        var file = new File(logFilePath);
        var logFile = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        logFile.println(word);
        logFile.close();
    }
}
