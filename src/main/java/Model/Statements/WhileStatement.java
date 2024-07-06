package Model.Statements;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.Collections.MyIDictionary;
import Model.Collections.MyIStack;
import Model.Expressions.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class WhileStatement implements IStatement{
    private IExpression expr;
    private IStatement stmt;

    public WhileStatement(IExpression e, IStatement s){
        expr = e;
        stmt = s;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtExecException, ExprEvalException, ADTException {
        Value value = expr.eval(state.getSymTable(), state.getHeap());
        MyIStack<IStatement> stack = state.getExeStack();
        if(value.getType().equals(new BoolType())){
            BoolValue boolValue = (BoolValue) value;
            if (boolValue.getVal()){
                stack.push(this);
                stack.push(stmt);
            }
        }
        else
            throw new StmtExecException("Value is not of type Bool!");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expr.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expr.toString(), stmt.toString());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        Type typexpr = expr.typeCheck(typeEnv);
        if (typexpr.equals(new BoolType())){
            stmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new StmtExecException("The condition of While does not have the type bool!");
    }
}
