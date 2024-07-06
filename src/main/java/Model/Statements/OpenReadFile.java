package Model.Statements;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.Expressions.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.*;
import Model.Value.*;
import Model.Collections.*;
import Model.Statements.IStatement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class OpenReadFile implements IStatement{
    private IExpression expr;

    public OpenReadFile(IExpression e){
        expr = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtExecException, ExprEvalException, ADTException {
        Value value = expr.eval(state.getSymTable(), state.getHeap());
        if(value.getType().equals(new StringType())){
            StringValue fileName = (StringValue) value;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if(!fileTable.exists(fileName.getVal())){
                BufferedReader br;
                try{
                    br = new BufferedReader(new FileReader(fileName.getVal()));
                }
                catch (FileNotFoundException e){
                    throw new StmtExecException("File could not be opened!");
                }
                fileTable.insert(fileName.getVal(), br);
                state.setFileTable(fileTable);
            }
            else
                throw  new StmtExecException("File is already opened!");
        }
        else throw new StmtExecException("Input is not StringType");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFile(expr.deepCopy());
    }

    public String toString(){
        return String.format("OpenReadFile(%s)", expr.toString());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        if (expr.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new StmtExecException("OpenReadFile requires a string expression!");
    }
}