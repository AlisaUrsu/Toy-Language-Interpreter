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

public class VariableDeclarationStatement implements IStatement{
    private String name;
    private Type type;

    public VariableDeclarationStatement(String n, Type t){
        name = n;
        type = t;
    }

    public ProgramState execute(ProgramState state) throws StmtExecException, ExprEvalException, ADTException{
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.exists(name))
            throw new StmtExecException("Variable " + name + " already exists!");
        symTable.insert(name, type.defaultValue());
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(name, type);
    }

    @Override
    public String toString() {
        return type.toString() + name;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        typeEnv.insert(name, type);
        return typeEnv;
    }
}