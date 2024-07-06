package Exceptions;

public class StmtExecException extends Exception{
    public StmtExecException(){
        super();
    }

    public StmtExecException(String message){
        super(message);
    }
}