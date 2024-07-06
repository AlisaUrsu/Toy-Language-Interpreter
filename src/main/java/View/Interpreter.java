package View;

import Controller.Controller;
import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.Collections.MyDictionary;
import Model.Collections.MyHeap;
import Model.Collections.MyList;
import Model.Collections.MyStack;
import Model.Expressions.*;
import Model.ProgramState.ProgramState;
import Model.Statements.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;

import java.io.IOException;

public class Interpreter {
    public static void main(String[] args) throws IOException, ADTException {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        try {
            ex1.typeCheck(new MyDictionary<>());
            ProgramState prg1 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex1);
            IRepository repo1 = new Repository(prg1, "log1.txt");
            Controller ctr1 = new Controller(repo1);
            menu.addCommand(new RunExampleCommand("1", ex1.toString(), ctr1));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }


        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        try {
            ex2.typeCheck(new MyDictionary<>());
            ProgramState prg2 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex2);
            IRepository repo2 = new Repository(prg2, "log2.txt");
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }


        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        try {
            ex3.typeCheck(new MyDictionary<>());
            ProgramState prg3 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex3);
            IRepository repo3 = new Repository(prg3, "log3.txt");
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExampleCommand("3", ex3.toString(), ctr3));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }


        IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("y", new IntType()),
                        new CompoundStatement(new AssignStatement("x", new ValueExpression(new IntValue(13))),
                                new CompoundStatement(new AssignStatement("y", new ValueExpression(new IntValue(10))),
                                        new IfStatement(new RelationalExpression("<", new VariableExpression("x"), new VariableExpression("y")), new PrintStatement(new VariableExpression("x")), new PrintStatement(new VariableExpression("y")))))));

        try {
            ex4.typeCheck(new MyDictionary<>());
            ProgramState prg4 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex4);
            IRepository repo4 = new Repository(prg4, "log4.txt");
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExampleCommand("4", ex4.toString(), ctr4));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }

        IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("filename", new StringType()),
                new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("ex5.in"))),
                        new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("filename"), "v"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("filename"), "v"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                        new CloseReadFile(new VariableExpression("filename"))))))))));

        try {
            ex5.typeCheck(new MyDictionary<>());
            ProgramState prg5 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex5);
            IRepository repo5 = new Repository(prg5, "log5.txt");
            Controller ctr5 = new Controller(repo5);
            menu.addCommand(new RunExampleCommand("5", ex5.toString(), ctr5));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }



        IStatement ex6 = new CompoundStatement(new VariableDeclarationStatement("filename", new StringType()), new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
                new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("ex6.in"))), new CompoundStatement(new AssignStatement("x", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new OpenReadFile(new VariableExpression("filename")), new CompoundStatement(new VariableDeclarationStatement("y", new IntType()),
                                new CompoundStatement(new ReadFile(new VariableExpression("filename"), "y"), new CompoundStatement(new CloseReadFile(new VariableExpression("filename")),
                                       new IfStatement(new RelationalExpression(">=", new VariableExpression("x"), new VariableExpression("y")), new PrintStatement(new VariableExpression("x")), new PrintStatement(new VariableExpression("y")))))))))));

        try {
            ex6.typeCheck(new MyDictionary<>());
            ProgramState prg6 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex6);
            IRepository repo6 = new Repository(prg6, "log6.txt");
            Controller ctr6 = new Controller(repo6);
            menu.addCommand(new RunExampleCommand("6", ex6.toString(), ctr6));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }


        IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(3))), new CompoundStatement(new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v",
                        new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))), new PrintStatement(new VariableExpression("v")))));

        try {
            ex7.typeCheck(new MyDictionary<>());
            ProgramState prg7 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex7);
            IRepository repo7 = new Repository(prg7, "log7.txt");
            Controller ctr7 = new Controller(repo7);
            menu.addCommand(new RunExampleCommand("7", ex7.toString(), ctr7));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }


        IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())), new CompoundStatement(new NewStatement("a",
                new ValueExpression(new IntValue(15))), new CompoundStatement(new VariableDeclarationStatement("b", new RefType(new RefType(new IntType()))),
                        new CompoundStatement(new NewStatement("b", new VariableExpression("a")), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))),
                                new PrintStatement(new ArithmeticExpression('+', new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("b"))), new ValueExpression(new IntValue(5)))))))));

        try {
            ex8.typeCheck(new MyDictionary<>());
            ProgramState prg8 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex8);
            IRepository repo8 = new Repository(prg8, "log8.txt");
            Controller ctr8 = new Controller(repo8);
            menu.addCommand(new RunExampleCommand("8", ex8.toString(), ctr8));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }


        IStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())), new CompoundStatement(new NewStatement("a",
                new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("b", new RefType(new RefType(new IntType()))),
                new CompoundStatement( new NewStatement("b", new VariableExpression("a")),  new CompoundStatement(new NewStatement("a",
                        new ValueExpression(new IntValue(30))), new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("b")))))))));

        try {
            ex9.typeCheck(new MyDictionary<>());
            ProgramState prg9 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex9);
            IRepository repo9 = new Repository(prg9, "log9.txt");
            Controller ctr9 = new Controller(repo9);
            menu.addCommand(new RunExampleCommand("9", ex9.toString(), ctr9));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }


        IStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())), new CompoundStatement(new NewStatement("v",
                new ValueExpression(new IntValue(20))), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(40))), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(99))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v")))))));

        try {
            ex10.typeCheck(new MyDictionary<>());
            ProgramState prg10 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex10);
            IRepository repo10 = new Repository(prg10, "log10.txt");
            Controller ctr10 = new Controller(repo10);
            menu.addCommand(new RunExampleCommand("10", ex10.toString(), ctr10));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }


        IStatement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

        try {
            ex11.typeCheck(new MyDictionary<>());
            ProgramState prg11 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex11);
            IRepository repo11 = new Repository(prg11, "log11.txt");
            Controller ctr11 = new Controller(repo11);
            menu.addCommand(new RunExampleCommand("11", ex11.toString(), ctr11));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }

        IStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("counter", new IntType()), new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                new WhileStatement(new RelationalExpression("<", new VariableExpression("counter"), new ValueExpression(new IntValue(10))),
                        new CompoundStatement(new ForkStatement(new ForkStatement(new CompoundStatement(new NewStatement("a", new VariableExpression("counter")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))) )), new AssignStatement("counter",
                                new ArithmeticExpression('+', new VariableExpression("counter"), new ValueExpression(new IntValue(1))))))));

        try {
            ex12.typeCheck(new MyDictionary<>());
            ProgramState prg12 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex12);
            IRepository repo12 = new Repository(prg12, "log12.txt");
            Controller ctr12 = new Controller(repo12);
            menu.addCommand(new RunExampleCommand("12", ex12.toString(), ctr12));
        }
        catch (ExprEvalException | ADTException | StmtExecException e){
            System.out.println(e.getMessage());
        }

        menu.show();
    }
}
