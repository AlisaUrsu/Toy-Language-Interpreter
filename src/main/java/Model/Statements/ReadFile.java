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
import java.io.IOException;

public class ReadFile implements IStatement{
    private IExpression expr;
    private String varName;

    public ReadFile(IExpression e, String vn){
        expr = e;
        varName = vn;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtExecException, ExprEvalException, ADTException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();

        if (symTable.exists(varName)){
            Value value = symTable.lookUp(varName);
            if (value.getType().equals(new IntType())){
                value = expr.eval(symTable, state.getHeap());
                if(value.getType().equals(new StringType())){
                    StringValue fileName = (StringValue) value;
                    if(fileTable.exists(fileName.getVal())){
                        BufferedReader br;
                        try{
                            br = fileTable.lookUp(fileName.getVal());
                            String line = br.readLine();
                            if (line == null || line.isEmpty())
                                line = "0";
                            symTable.insert(varName, new IntValue(Integer.parseInt(line)));
                        }
                        catch (IOException e){
                            throw new StmtExecException("Could not read from file!");
                        }
                    }
                    else
                        throw new StmtExecException(String.format("The file table does not contain %s", fileName));
                }
                else
                     throw new StmtExecException("Does not evaluate to StringType!");
            }
            else
                throw new StmtExecException("Is not of StringType!");
        }
        else
            throw  new StmtExecException("The variable name is not present in the symbol table!");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(expr.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return String.format("ReadFile(%s, %s)", expr.toString(), varName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        if (expr.typeCheck(typeEnv).equals(new StringType()))
            if (typeEnv.lookUp(varName).equals(new IntType()))
                return typeEnv;
            else
                throw new StmtExecException("ReadFile requires an int as it variable name!");
        else
            throw new StmtExecException("ReadFile requires a string as expression!");
    }

}
