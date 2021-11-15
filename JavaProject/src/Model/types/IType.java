package Model.types;

import Model.value.IValue;

public interface IType {
    IValue defaultValue();
    IType deepCopy();
    String getType();
    String toString();
    boolean equals(Object o);
}
