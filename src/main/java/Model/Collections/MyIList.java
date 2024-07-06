package Model.Collections;
import Exceptions.ADTException;

import java.util.ArrayList;

public interface MyIList<T> {
    T pop() throws ADTException;
    void remove(T thing) throws ADTException;
    void add(T elem);
    boolean isEmpty();
    int size();
    void clear();
    boolean contains(T elem);
    T get(int from);
    void set(int where, T elem);
    ArrayList<T> getList();

}


