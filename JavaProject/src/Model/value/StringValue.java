package Model.value;

import Model.types.IType;
import Model.types.StringType;

public class StringValue implements IValue{
    String value;

    public StringValue(){
        value = "";
    }

    public StringValue(String string){
        this.value = string;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        StringValue o_value = (StringValue) o;
        return o_value.value == this.value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public String toString(){
        return value;
    }
}
