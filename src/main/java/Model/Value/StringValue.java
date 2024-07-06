package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value{
    private String val;

    public StringValue(String s){
        val = s;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public String getVal() {
        return val;
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof StringValue))
            return false;
        StringValue cast = (StringValue) other;
        return val.equals(cast.val);
    }

    @Override
    public String toString() {
        return val;
    }
}