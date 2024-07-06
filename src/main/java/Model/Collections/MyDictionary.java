package Model.Collections;

import Exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T1, T2> implements MyIDictionary<T1, T2>{
    private Map<T1, T2> dictionary;

    public MyDictionary(){
        dictionary = new HashMap<>();
    }

    @Override
    public int size() {
        return dictionary.size();
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public void insert(T1 key, T2 value) {
        dictionary.put(key, value);
    }

    @Override
    public void removeItem(T1 key) throws ADTException {
        if(!exists(key))
            throw new ADTException("This key does not exist!");
        dictionary.remove(key);
    }

    @Override
    public boolean exists(T1 key) {
        return dictionary.containsKey(key);
    }

    @Override
    public T2 lookUp(T1 key) throws ADTException {
        if(!exists(key))
            throw new ADTException("This key does not exist!");
        return dictionary.get(key);
    }

    @Override
    public Set<T1> getSet() {
        return dictionary.keySet();
    }

    @Override
    public Collection<T2> values() {
        return dictionary.values();
    }

    @Override
    public void update(T1 key, T2 value) throws ADTException {
        if(!(exists(key)))
            throw new ADTException(key + " does not exist.");
        dictionary.put(key, value);
    }

    @Override
    public Map<T1, T2> getDictContent() {
        return dictionary;
    }

    @Override
    public void clear() {
        dictionary.clear();
    }

    @Override
    public MyIDictionary<T1, T2> deepCopy() throws ADTException {
        MyIDictionary<T1, T2> newDict = new MyDictionary<>();
        for (T1 key: getSet())
            newDict.insert(key, lookUp(key));
        return newDict;
    }
}