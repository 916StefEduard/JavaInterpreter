package Model.types;

import Model.value.IValue;

public interface IType {
    IValue defaultValue();
    IType deepCopy();
    IType getType();
    String toString();
    boolean equals(Object o);
}
