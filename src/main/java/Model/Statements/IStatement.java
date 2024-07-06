package Model.Statements;
import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.Collections.MyIDictionary;
import Model.ProgramState.ProgramState;
import Model.Type.Type;

public interface IStatement {
    ProgramState execute(ProgramState state) throws StmtExecException, ExprEvalException, ADTException;
    public IStatement deepCopy();
    String toString();
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException;
}
