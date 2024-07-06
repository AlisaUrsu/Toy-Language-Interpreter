package Model.Statements;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.Collections.MyIDictionary;
import Model.Expressions.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStatement implements IStatement{
    private String id;
    private IExpression expr;

    public ProgramState execute(ProgramState state) throws StmtExecException, ADTException, ExprEvalException{
        MyIDictionary<String, Value> symTable = state.getSymTable();

        if(symTable.exists(id)){
            Value value = expr.eval(symTable, state.getHeap());
            Type typeId = (symTable.lookUp(id)).getType();
            if(value.getType().equals(typeId)){
                symTable.update(id, value);
            }
            else
                throw new StmtExecException("Declared type of variable " + id + " and type of the assigned expression do not match.");
        }
        else
            throw new StmtExecException("The used variable " + id + " was not declared before");
        state.setSymTable(symTable);
        return null;
    }

    public AssignStatement(String i, IExpression e){
        id = i;
        expr = e;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(id, expr.deepCopy());
    }

    @Override
    public String toString() {
        return id + " = " + expr.toString();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        Type typevar = typeEnv.lookUp(id);
        Type typexp = expr.typeCheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new StmtExecException("Assignment: right hand side and left hand side have different types ");
    }
}