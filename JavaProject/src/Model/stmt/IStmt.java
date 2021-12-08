package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public interface IStmt {
     String toString();
     PrgState execute(PrgState state) throws  Exception;
     IStmt deepcopy();
     String getStatement();
     IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception;
}
