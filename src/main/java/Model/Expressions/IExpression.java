package Model.Expressions;
import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Model.Collections.MyIDictionary;
import Model.Collections.MyIHeap;
import Model.Type.Type;
import Model.Value.Value;

public interface IExpression {
    Value eval(MyIDictionary<String, Value> tbl, MyIHeap hp) throws ExprEvalException, ADTException;
    IExpression deepCopy();
    String toString();
    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExprEvalException, ADTException;
}