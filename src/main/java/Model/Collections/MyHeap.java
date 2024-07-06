package Model.Collections;

import Exceptions.ADTException;
import Model.Value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap implements MyIHeap{
    private Map<Integer, Value> heap;
    private int freeLocation;

    public MyHeap(){
        heap = new HashMap<>();
        freeLocation = 1;
    }

    public int newValue(){
        //freeLocation += 1;
        while(exists(freeLocation))
            freeLocation += 1;
        return freeLocation;
    }

    @Override
    public int add(Value value) {
        heap.put(freeLocation, value);

        int returnVal = freeLocation;
        freeLocation = newValue();
        return returnVal;
    }

    @Override
    public boolean exists(Integer pos) {
        return heap.containsKey(pos);
    }

    @Override
    public Set<Integer> getSet() {
        return heap.keySet();
    }

    @Override
    public Map<Integer, Value> getHeapContent() {
        return heap;
    }

    @Override
    public void setHeap(Map<Integer, Value> heap) {
        this.heap = heap;
    }

    @Override
    public Value lookUp(Integer key) throws ADTException {
        if(!exists(key))
            throw new ADTException("The key is not present in the heap!");
        return heap.get(key);
    }

    @Override
    public void remove(Integer key) throws ADTException {
        if(!exists(key))
            throw new ADTException("The key is not present in the heap!");
        heap.remove(key);
    }

    @Override
    public void update(Integer pos, Value value) throws ADTException {
        if(!exists(pos))
            throw new ADTException("The key is not present in the heap!");
        heap.put(pos, value);
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    @Override
    public void clear() {
        heap.clear();
    }
}
