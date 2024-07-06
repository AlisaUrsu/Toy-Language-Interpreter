package Model.Expressions;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Model.Type.Type;
import Model.Value.*;
import Model.Collections.*;

public class ValueExpression implements IExpression{
    private Value expr;

    public ValueExpression(Value e){
        expr = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap hp) throws ExprEvalException {
        return expr;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(expr);
    }

    @Override
    public String toString() {
        return expr.toString();
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExprEvalException, ADTException {
        return expr.getType();
    }
}