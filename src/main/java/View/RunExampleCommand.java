package View;

import Controller.Controller;
import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;

import java.io.IOException;
import java.util.Scanner;

public class RunExampleCommand extends Command{
    private Controller controller;

    public RunExampleCommand(String k, String d, Controller ctr){
        super(k, d);
        controller = ctr;
    }

    @Override
    public void execute() {
        try{
            System.out.println("Do you want to display the steps? (Y|N)");
            Scanner readOpt = new Scanner(System.in);
            String opt = readOpt.next();

            controller.setDisplayFlag(opt.equals("Y"));
            controller.allStep();
        }
        catch (ExprEvalException | ADTException | StmtExecException | IOException | InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}
