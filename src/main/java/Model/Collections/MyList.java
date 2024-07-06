package Model.Collections;
import Exceptions.ADTException;
import Model.Collections.MyIList;

import java.util.ArrayList;
public class MyList<T> implements MyIList<T> {
    private ArrayList<T> list;

    public MyList(){
        list = new ArrayList<>();
    }

    public int size(){
        return list.size();
    }

    @Override
    public T pop() throws ADTException {
        return list.remove(0);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(T elem) {
        return list.contains(elem);
    }

    @Override
    public void add(T elem) {
        list.add(elem);
    }

    @Override
    public void remove(T elem) throws ADTException {
        list.remove(elem);
    }

    @Override
    public T get(int from) {
        return list.get(from);
    }

    @Override
    public void set(int where, T elem) {
        list.set(where, elem);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public ArrayList<T> getList() {
        return list;
    }
}