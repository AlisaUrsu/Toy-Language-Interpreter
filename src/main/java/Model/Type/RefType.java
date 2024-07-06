package Model.Type;


import Model.Value.RefValue;
import Model.Value.Value;


public class RefType implements Type{
    private Type inner;

    public RefType(Type i){
        inner = i;
    }

    public Type getInner(){
        return inner;
    }

    public boolean equals(Object other){
        return other instanceof RefType;
    }

    public String toString(){
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public Value defaultValue() {
        return new RefValue(1, inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }
}
