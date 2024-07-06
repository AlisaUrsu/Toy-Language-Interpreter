package Model.Type;

import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type{
    @Override
    public boolean equals(Object other) {
        return other instanceof StringType;
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "string ";
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }
}