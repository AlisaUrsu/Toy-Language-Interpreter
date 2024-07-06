package Model.Collections;

import Exceptions.ADTException;
import Model.Value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface MyIHeap {
    boolean exists(Integer pos);
    Map<Integer, Value> getHeapContent();
    void setHeap(Map<Integer, Value> newHeap);
    Set<Integer> getSet();
    int add(Value value);
    void update(Integer pos, Value value) throws ADTException;
    void remove(Integer key) throws ADTException;
    Value lookUp(Integer key) throws ADTException;
    String toString();
    void clear();
}
