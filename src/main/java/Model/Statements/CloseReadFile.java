package Model.Statements;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.Collections.MyIDictionary;
import Model.Expressions.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements IStatement{
    private IExpression expr;

    public CloseReadFile(IExpression e){
        expr = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtExecException, ExprEvalException, ADTException {
        Value value = expr.eval(state.getSymTable(), state.getHeap());
        if(value.getType().equals(new StringType())){
            StringValue fileName = (StringValue) value;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if(fileTable.exists(fileName.getVal())){
                BufferedReader br;
                try{
                    br = fileTable.lookUp(fileName.getVal());
                    br.close();
                }
                catch (IOException e){
                    throw new StmtExecException("Error in closing the file!");
                }
                fileTable.removeItem(fileName.getVal());
                state.setFileTable(fileTable);
            }
            else
                throw new StmtExecException("The file is not present in the file table!");
        }
        else
            throw new StmtExecException(String.format("%s does not evaluate to StringType!", value));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseReadFile(expr.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("CloseReadFile(%s)", expr.toString());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        if (expr.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new StmtExecException("CloseReadFile requires a string expression!");
    }
}
