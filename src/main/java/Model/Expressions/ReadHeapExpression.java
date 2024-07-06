package Model.Expressions;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Model.Collections.MyIDictionary;
import Model.Collections.MyIHeap;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class ReadHeapExpression implements IExpression{
    private IExpression expr;

    public ReadHeapExpression(IExpression e){
        expr = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap hp) throws ExprEvalException, ADTException {
        Value value = expr.eval(tbl, hp);
        if(value.getType() instanceof RefType) {
            RefValue refValue = (RefValue) value;
            return hp.lookUp(refValue.getAddress());
        }
        else
            throw new ExprEvalException("Not of RefType!");
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(expr.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("ReadHeap(%s)", expr);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExprEvalException, ADTException {
        Type type = expr.typeCheck(typeEnv);
        if (type instanceof RefType){
            RefType reft = (RefType) type;
            return reft.getInner();
        }
        else
            throw new ExprEvalException("The ReadHeap argument is not of Ref Type");
    }
}
