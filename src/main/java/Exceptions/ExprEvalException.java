package Exceptions;

public class ExprEvalException extends Exception{
    public ExprEvalException(){
        super();
    }

    public ExprEvalException(String message){
        super(message);
    }
}
