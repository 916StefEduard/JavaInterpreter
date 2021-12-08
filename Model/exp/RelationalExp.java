package Model.exp;
import Exception.*;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.Objects;

public class RelationalExp implements Exp {
    String op;
    Exp e1,e2;

    public RelationalExp(String op,Exp e1,Exp e2){
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws Exception {
        IValue v1,v2;
        v1 = e1.eval(symTable,heap);
        if(v1.getType().equals(new IntType().getType())){
            v2 = e2.eval(symTable,heap);
            if(v2.getType().equals(new IntType().getType())) {
                var i1 = (IntValue) v1;
                var i2 = (IntValue) v2;
                int n1 = i1.getValue();
                int n2 = i2.getValue();
                if (Objects.equals(op, "<"))
                    return new BoolValue(n1 < n2);
                else if (Objects.equals(op, "<="))
                    return new BoolValue(n1 <= n2);
                else if (Objects.equals(op, "=="))
                    return new BoolValue(n1 == n2);
                else if (Objects.equals(op, "!="))
                    return new BoolValue(n1 != n2);
                else if (Objects.equals(op, ">")) {
                    if (n1 > n2)
                        return new BoolValue(true);
                    else
                        return new BoolValue(false);
                }
                else if (Objects.equals(op, ">="))
                    return new BoolValue( n1 >= n2);
            }else throw new InputException("second operand is not int");
        }else throw new InputException("first operand is not int");
        return new BoolValue();
    }

    public String getOperation(){
        return op;
    }

    public Exp getFirstExpression(){
        return this.e1;
    }

    public Exp getSecondExpression(){
        return this.e2;
    }

    @Override
    public String toString() {
        return this.e1 + this.op + this.e2;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type1,type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if(type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else {
                throw new Exception("Second operand is not an integer\n");
            }
        }else{
            throw new Exception("First operand is not an integer\n");
        }
    }
}
