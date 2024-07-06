package Model.Statements;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.Collections.MyIDictionary;
import Model.Collections.MyIHeap;
import Model.Expressions.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class NewStatement implements IStatement{
    private String varName;
    private IExpression expr;

    public NewStatement(String v, IExpression e){
        varName = v;
        expr = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtExecException, ExprEvalException, ADTException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if(symTable.exists(varName)){
            Value var = symTable.lookUp(varName);
            if (var.getType() instanceof RefType){
                Value evaluated = expr.eval(symTable, heap);
                Type locationType = ((RefValue) var).getLocationType();
                if (locationType.equals(evaluated.getType())){
                    int newPosition = heap.add(evaluated);
                    symTable.insert(varName, new RefValue(newPosition, locationType));
                    state.setSymTable(symTable);
                    state.setHeap(heap);
                    return null;
                }
                else
                    throw new StmtExecException("The types are not the same!");
            }
            else
                throw new StmtExecException("Not of RefType!");
        }
        else
            throw new StmtExecException("Not in symTable!");
    }

    @Override
    public IStatement deepCopy() {
        return new NewStatement(varName, expr.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", varName, expr.toString());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        Type typevar = typeEnv.lookUp(varName);
        Type typexp = expr.typeCheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new StmtExecException("NEW stmt: right hand side and left hand side have different types!");
    }
}
