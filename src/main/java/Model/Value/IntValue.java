package Model.Value;
import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value{
    private int val;

    public IntValue(int v){
        val = v;
    }

    public int getVal(){
        return val;
    }

    public String toString(){
        return String.format("%d", val);
    }

    public Type getType(){
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof IntValue))
            return false;
        IntValue cast = (IntValue) other;
        return val == cast.val;
    }
}