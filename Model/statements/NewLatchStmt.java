package model.statements;
import exceptions.MyException;
import exceptions.TypeMismatchException;
import exceptions.UndefinedException;
import model.PrgState;
import model.adt.IDict;
import model.expressions.IExp;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class NewLatchStmt implements IStmt{
    private static int nextFree = -1;
    private String var;
    private IExp exp;

    public NewLatchStmt(String var,IExp exp){
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        try{
            IValue oldValue = this.exp.eval(state.getSymTable(),state.getHeapTable());
            if(!oldValue.getType().equals(new IntType())){
                throw new TypeMismatchException("Value is not of type int\n");
            }
            IntValue number = (IntValue) oldValue;
            int value = number.getValue();
            synchronized (state.getLatchTable()){
                state.getLatchTable().put(nextFree,value);
                if(state.getSymTable().isDefined(this.var)) {
                    nextFree++;
                    state.getSymTable().update(this.var, new IntValue(nextFree));
                    return null;
                }else{
                    throw new UndefinedException("Variable is not present in the symbolTable\n");
                }
            }
        }catch(MyException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        var typeVar = typeEnv.lookup(var);
        var typeExp = exp.typeCheck(typeEnv);
        if(typeVar.equals(new IntType()) && typeExp.equals(new IntType())){
            return typeEnv;
        }else{
            throw new TypeMismatchException("Variable and Exp are not correct");
        }
    }

    @Override
    public String toString() {
        return "newLatch(" + this.var + "," + this.exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
