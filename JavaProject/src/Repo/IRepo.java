package Repo;
import Model.PrgState;
import Exception.*;

import java.io.IOException;

public interface IRepo {
    void addPrg(PrgState newPrg);
    PrgState getCrtPrg() throws RepositoryException;
    public void logPrgStateExec(String word) throws IOException;
    }
