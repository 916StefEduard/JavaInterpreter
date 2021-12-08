package Model.types;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.RefValue;

public class RefType implements IType{
    IType inner;

    public RefType(IType inner){
        this.inner = inner;
    }

    public IType getInner(){
        return this.inner;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() == inner.getClass()){
            return inner.equals(this.getInner());
        }else
            return false;
    }

    @Override
    public String toString(){
        return "Ref (" + inner.toString() + ")";
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(new IntValue(0),inner);
    }

    @Override
    public IType deepCopy() {
        return new RefType(this.inner);
    }

    @Override
    public IType getType() {
        return this.inner;
    }
}
