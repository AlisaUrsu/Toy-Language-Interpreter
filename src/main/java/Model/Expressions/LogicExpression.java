package Model.Expressions;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Model.Type.*;
import Model.Value.*;
import Model.Collections.*;

public class LogicExpression implements IExpression{
    private IExpression expr1;
    private IExpression expr2;
    private String operation; //1-and 2-or
                      //la fel si aici
    public LogicExpression(IExpression e1, IExpression e2, String op){
        expr1 = e1;
        expr2 = e2;
        operation = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap hp) throws ExprEvalException, ADTException {
        Value value1, value2;
        value1 = expr1.eval(tbl, hp);
        if (value1.getType().equals(new BoolType())){
            value2 = expr2.eval(tbl, hp);
            if (value2.getType().equals(new BoolType())){
                BoolValue bool1 = (BoolValue) value1;
                BoolValue bool2 = (BoolValue) value2;
                boolean op1, op2;
                op1 = bool1.getVal();
                op2 = bool2.getVal();
                if(operation.equals("and"))
                    return new BoolValue(op1 && op2);
                if(operation.equals("or"))
                    return new BoolValue(op1 || op2);
            }
            else
                throw new ExprEvalException("Second operand is not boolean.");
        }
        else
            throw new ExprEvalException("First operand is not boolean.");
        return null;
    }

    @Override
    public IExpression deepCopy() {
        return new LogicExpression(expr1.deepCopy(), expr2.deepCopy(), operation);
    }

    @Override
    public String toString() {
        return expr1.toString() + " " + operation + " " + expr2.toString();
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExprEvalException, ADTException {
        Type type1, type2;
        type1 = expr1.typeCheck(typeEnv);
        type2 = expr2.typeCheck(typeEnv);

        if (type1.equals(new BoolType()))
            if (type2.equals(new BoolType()))
                return new BoolType();
            else
                throw  new ExprEvalException("Second operator is not boolean!");
        else
            throw new ExprEvalException("First operator is not boolean!");
    }
}