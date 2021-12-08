package Model.value;
import Model.types.IType;
import Model.types.IntType;
import Model.types.RefType;


public class RefValue implements IValue{
    IntValue HeapAddress;
    IType locationType;

    public RefValue(){
        this.HeapAddress = new IntValue(0);
        this.locationType = new IntType();
    }

    public RefValue(IntValue HeapAddress,IType type){
        this.HeapAddress = HeapAddress;
        this.locationType = type;
    }

    @Override
    public IType getType() {
        return new RefType(this.locationType).getType();
    }

    public IType getLocationType(){
        return new RefType(this.locationType);
    }

    public Integer getHeadAddress(){
        return Integer.valueOf(String.valueOf(this.HeapAddress));
    }


    @Override
    public String toString(){
        return "(" +  this.HeapAddress + "," + this.locationType + ")";
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(this.HeapAddress,this.locationType);
    }
}
