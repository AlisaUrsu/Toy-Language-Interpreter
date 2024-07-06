package Model.Collections;
import Exceptions.ADTException;

import java.util.Stack;

public interface MyIStack<T> {
    T pop() throws ADTException;
    void push(T elem);
    boolean isEmpty();
    int size();
    T peek();
    String toString();
    Stack<T> getStack();

    void clear();
}