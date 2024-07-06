package Model.Statements;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.Collections.MyIDictionary;
import Model.Collections.MyIStack;
import Model.ProgramState.ProgramState;
import Model.Type.Type;

public class CompoundStatement implements IStatement{
    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement f, IStatement s){
        first = f;
        second = s;
    }

    public ProgramState execute(ProgramState state) throws ADTException{
        MyIStack<IStatement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() +")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        MyIDictionary<String,Type> typEnv1 = first.typeCheck(typeEnv);
        MyIDictionary<String,Type> typEnv2 = second.typeCheck(typEnv1);
        return typEnv2;
    }
}