package Model.Value;
import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value{
    private boolean val;

    public BoolValue(boolean v){
        val = v;
    }

    public boolean getVal(){
        return val;
    }

    public String toString(){
        return val ? "true" : "false";
    }

    public Type getType(){
        return new BoolType();
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof BoolValue))
            return false;
        BoolValue cast = (BoolValue) other;
        return val == cast.val;
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }
}