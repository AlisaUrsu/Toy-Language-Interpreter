package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value{
    private int address;
    private Type locationType;

    public RefValue(int a, Type l){
        address = a;
        locationType = l;
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", address, locationType.toString());
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    public int getAddress(){
        return address;
    }

    public Type getLocationType(){
        return locationType;
    }

    public Value deepCopy(){
        return new RefValue(address, locationType.deepCopy());
    }


}
