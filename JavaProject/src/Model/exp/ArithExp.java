package Model.exp;
import Model.adt.IDict;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;
import Exception.DivisionByZeroException;
import Exception.InvalidOperandException;
import Model.value.StringValue;

public class ArithExp implements Exp{
    char op;
    Exp e1, e2;

    public ArithExp(char op,Exp e1, Exp e2){
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    public IValue eval(IDict<String, IValue> symTable) throws Exception {
        IValue v1,v2;
        v1 = e1.eval(symTable);
        if(v1.getType().equals(new IntType())){
            v2 = e2.eval(symTable);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1,n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if(op == '+')
                    return new IntValue(n1+n2);
                if(op == '-')
                    return new IntValue(n1-n2);
                if(op == '*')
                    return new IntValue(n1*n2);
                if(op == '/')
                    if(n2 == 0)
                        throw new DivisionByZeroException("division by zero");
                    else
                        return new IntValue(n1/n2);
            }else{
                throw new InvalidOperandException("second operand is not an integer");
            }
        }else
            throw new InvalidOperandException("third operand is not an integer");
        return null;
    }

    public char getOp() {
        return this.op;
    }

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
}
