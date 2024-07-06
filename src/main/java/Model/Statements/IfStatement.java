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

public class IfStatement implements IStatement{
    private IExpression expr;
    private IStatement thenS;
    private IStatement elseS;

    public IfStatement(IExpression ex, IStatement t, IStatement e){
        expr = ex;
        thenS = t;
        elseS = e;
    }

    public ProgramState execute(ProgramState state) throws StmtExecException, ExprEvalException, ADTException{
        Value result = expr.eval(state.getSymTable(), state.getHeap());
        if (result instanceof BoolValue boolResult){
            IStatement stmt;
            if(boolResult.getVal())
                stmt = thenS;
            else
                stmt = elseS;
            MyIStack<IStatement> stack = state.getExeStack();
            stack.push(stmt);
            state.setExeStack(stack);
            return null;
        }
        else throw new StmtExecException("Please provide a boolean expression in the if statement.");
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expr.deepCopy(), thenS.deepCopy(),elseS.deepCopy());
    }

    @Override
    public String toString() {
        return "(IF(" + expr.toString() +") THEN(" + thenS.toString() +") ELSE(" + elseS.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        Type typexp = expr.typeCheck(typeEnv);
        if (typexp.equals(new BoolType())){
            thenS.typeCheck(typeEnv.deepCopy());
            elseS.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new StmtExecException("The condition of IF does not have the type bool!");
    }
}