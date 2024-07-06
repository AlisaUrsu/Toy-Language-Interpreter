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

public class WriteHeapStatement implements IStatement{
    private String varName;
    private IExpression expr;

    public WriteHeapStatement(String v, IExpression e){
        varName = v;
        expr = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtExecException, ExprEvalException, ADTException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if (symTable.exists(varName)){
            Value value = symTable.lookUp(varName);
            if (value.getType() instanceof RefType){
                RefValue refValue = (RefValue) value;
                Value evaluated = expr.eval(symTable, heap);
                if (evaluated.getType().equals(refValue.getLocationType())){
                    heap.update(refValue.getAddress(), evaluated);
                    state.setHeap(heap);
                    return null;
                }
                else
                    throw new StmtExecException("The types are different!");
            }
            else
                throw new StmtExecException("Not of RefType!");
        }
        else
            throw new StmtExecException("Not in symTable!");
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(varName, expr.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varName, expr.toString());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StmtExecException, ADTException, ExprEvalException {
        if (typeEnv.lookUp(varName).equals(new RefType(expr.typeCheck(typeEnv))))
            return typeEnv;
        else
            throw new StmtExecException("WriteHeap: right hand side and left hand side have different types!");
    }
}
