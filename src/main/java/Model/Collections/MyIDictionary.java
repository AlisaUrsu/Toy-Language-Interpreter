package Model.Collections;
import Exceptions.ADTException;

import java.util.*;

public interface MyIDictionary<T1, T2> {
    void insert(T1 key, T2 value);
    void removeItem(T1 key) throws ADTException;
    int size();
    boolean isEmpty();
    T2 lookUp(T1 key) throws ADTException;
    boolean exists(T1 key);
    Set<T1> getSet();
    Collection<T2> values();
    void update(T1 key, T2 value) throws ADTException;
    Map<T1, T2> getDictContent();
    void clear();
    MyIDictionary<T1,T2> deepCopy() throws ADTException;
}