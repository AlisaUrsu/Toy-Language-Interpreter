package Model.Expressions;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Model.Type.*;
import Model.Value.*;
import Model.Collections.*;

import java.util.Objects;

public class RelationalExpression implements IExpression{
    IExpression expr1;
    IExpression expr2;
    String operator;

    public RelationalExpression(String op, IExpression e1, IExpression e2){
        expr1 = e1;
        expr2 = e2;
        operator = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap hp) throws ExprEvalException, ADTException {
        Value value1, value2;
        value1 = expr1.eval(tbl, hp);
        if(value1.getType().equals(new IntType())) {
            value2 = expr2.eval(tbl, hp);
            if (value2.getType().equals(new IntType())) {
                IntValue int1 = (IntValue) value1;
                IntValue int2 = (IntValue) value2;
                int n1, n2;
                n1 = int1.getVal();
                n2 = int2.getVal();
                if (operator.equals("<"))
                    return new BoolValue(n1 < n2);
                else if (operator.equals("<="))
                    return new BoolValue(n1 <= n2);
                else if (operator.equals("=="))
                    return new BoolValue(n1 == n2);
                else if (operator.equals("!="))
                    return new BoolValue(n1 != n2);
                else if (operator.equals(">"))
                    return new BoolValue(n1 > n2);
                else if (operator.equals(">="))
                    return new BoolValue(n1 >= n2);
                else
                    throw new ExprEvalException("Invalid operation!");
            }
            else
                throw new ExprEvalException("Second operand is not an integer.");
        }
        else
            throw new ExprEvalException("First operand is not an integer.");
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(operator, expr1.deepCopy(), expr2.deepCopy());
    }

    @Override
    public String toString() {
        return expr1.toString() + operator + expr2.toString();
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExprEvalException, ADTException {
        Type type1, type2;
        type1 = expr1.typeCheck(typeEnv);
        type2 = expr2.typeCheck(typeEnv);

        if (type1.equals(new IntType()))
            if (type2.equals(new IntType()))
                return new BoolType();
            else
                throw  new ExprEvalException("Second operator is not an integer!");
        else
            throw new ExprEvalException("First operator is not an integer!");
    }
}