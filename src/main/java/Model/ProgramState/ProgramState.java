package Model.ProgramState;

import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.Expressions.IExpression;
import Model.Type.*;
import Model.Value.*;
import Model.Collections.*;
import Model.Statements.IStatement;

import java.io.BufferedReader;

public class ProgramState {
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IStatement originalProgram;
    private MyIDictionary<String, BufferedReader> fileTable;
    private MyIHeap heap;
    private int id;
    private static int nextID = 0;

    public ProgramState(MyIStack<IStatement> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> o, MyIDictionary<String, BufferedReader> fltbl, MyIHeap h, IStatement prg){
        exeStack = stk;
        symTable = symtbl;
        out = o;
        fileTable = fltbl;
        heap = h;
        originalProgram = prg.deepCopy();
        exeStack.push(originalProgram);
        id = generateID();
    }

    public synchronized int generateID(){
        nextID++;
        return nextID;
    }

    public int getId(){
        return id;
    }

    public Boolean isNotCompleted(){
        return !(exeStack.isEmpty());
    }

    public ProgramState oneStep() throws StmtExecException, ADTException, ExprEvalException{
        if (exeStack.isEmpty())
            throw new StmtExecException("Program state stack is empty");
        IStatement crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public void setExeStack(MyIStack<IStatement> newStack){
        exeStack = newStack;
    }

    public void setSymTable(MyIDictionary<String, Value> newSymTable){
        symTable = newSymTable;
    }

    public void setOut(MyIList<Value> newOut){
        out = newOut;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> newFileTable){fileTable = newFileTable;}

    public void setHeap(MyIHeap heap) {
        this.heap = heap;
    }

    public MyIStack<IStatement> getExeStack(){
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable(){
        return symTable;
    }

    public MyIList<Value> getOut(){
        return out;
    }

    public MyIDictionary<String, BufferedReader> getFileTable(){return fileTable;}

    public MyIHeap getHeap(){ return heap;}

    public String exeStackToString(){
        String exeStackString = "";
        for(IStatement stmt: exeStack.getStack()){
            exeStackString += stmt.toString() + "\n";
        }
        return exeStackString;
    }

    public String symTableToString() throws ADTException {
        String symTableString = "";
        for(String id: symTable.getSet())
            symTableString += String.format("%s -> %s\n", id, symTable.lookUp(id).toString());
        return symTableString;
    }

    public String outToString(){
        String outString = "";
        for(Value val: out.getList())
            outString += String.format("%s\n", val.toString());
        return outString;
    }

    public String fileTableToString(){
        String fileTableString = "";
        for(String id: fileTable.getSet())
            fileTableString += String.format("%s\n", id);
        return fileTableString;
    }

    public String heapToString() throws ADTException {
        String heapString = "";
        for (int id: heap.getSet())
            heapString += String.format("%d -> %s\n", id, heap.lookUp(id).toString());
        return heapString;
    }

    public String PrgmStateToString() throws ADTException{
        return "ID: " + id + "\nExecution stack: \n" + exeStackToString() + "\nSymbol table: \n" + symTableToString()  +"\nOutput list: \n"
                + outToString() + "\nHeap: \n" + heapToString()+ "\nFile Table: \n" + fileTableToString();
    }

    public void resetProgram(){
        out.clear();
        symTable.clear();
        exeStack.clear();
        fileTable.clear();
        heap.clear();
        exeStack.push(originalProgram.deepCopy());
    }
}
