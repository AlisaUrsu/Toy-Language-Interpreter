package Model.Collections;
import Exceptions.ADTException;
import Model.Collections.MyIStack;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    private Stack<T> stack;

    public MyStack(){
        stack = new Stack<>();
    }
    @Override
    public T pop()throws ADTException{
        if(isEmpty())
            throw new ADTException("No elements in stack!");
        return stack.pop();
    };

    @Override
    public void push(T elem) {
        stack.push(elem);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public String toString(){
        return stack.toString();
    }

    @Override
    public Stack<T> getStack() {
        return stack;
    }

    @Override
    public void clear() {
        stack.clear();
    }
}