package Repo;
import Model.PrgState;
import Exception.*;

import java.io.IOException;
import java.util.ArrayList;

public interface IRepo {
    void addPrg(PrgState newPrg);
    public void logPrgStateExec(PrgState prgState) throws IOException, StackException;
    public String getFilePath();
    public ArrayList<PrgState> getMyPrgStates();
    public void setPrgList(ArrayList<PrgState> newPrgStates);
}
