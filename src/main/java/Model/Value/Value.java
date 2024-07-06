package Model.Value;
import Model.Type.Type;
public interface Value {
    boolean equals(Object other);
    Type getType();
    Value deepCopy();
}