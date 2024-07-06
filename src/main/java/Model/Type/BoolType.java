package Model.Type;

import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType implements Type {
    @Override
    public boolean equals(Object other) {
        return other instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool ";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }
}
