package Model.Statements;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.Collections.MyIDictionary;
import Model.Collections.MyIStack;
import Model.Collections.MyStack;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Model.Value.Value;

public class ForkStatement implements IStatement{
    private IStatement statement;

    public ForkStatement(IStatement s){
        statement = s;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtExecException, ExprEvalException, ADTException {
        MyIStack<IStatement> newStack = new MyStack<>();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<String, Value> newSymTbl = symTbl.deepCopy();

        return new ProgramState(newStack, newSymTbl, state.getOut(), state.getFileTable(), state.getHeap(), statement);
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Fork(%s)", statement.toString());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
