package Model.Expressions;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Model.Type.*;
import Model.Value.*;
import Model.Collections.*;

public class VariableExpression implements IExpression{
    private String id;

    public VariableExpression(String i){
        id = i;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap hp) throws ADTException {
        return tbl.lookUp(id);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExprEvalException, ADTException {
        return typeEnv.lookUp(id);
    }
}