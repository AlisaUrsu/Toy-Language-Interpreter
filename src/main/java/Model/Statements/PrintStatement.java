package Model.Statements;
import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.Collections.MyIDictionary;
import Model.Collections.MyIList;
import Model.Expressions.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStatement implements IStatement{
    private IExpression expr;

    public PrintStatement(IExpression e){
        expr = e;
    }

    public ProgramState execute(ProgramState state) throws ADTException, ExprEvalException {
        MyIList<Value> out = state.getOut();
        out.add(expr.eval(state.getSymTable(), state.getHeap()));
        state.setOut(out);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expr.deepCopy());
    }

    @Override
    public String toString() {
        return "Print(" + expr.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        expr.typeCheck(typeEnv);
        return typeEnv;
    }
}