package com.example.a7v2;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.util.ArrayList;
import java.util.List;

public class ProgramListController {
    private ProgramExecutionController executorController;

    public void setExecutorController(ProgramExecutionController execCtr){
        executorController = execCtr;
    }

    @FXML
    private ListView<IStatement> prgListView;
    @FXML
    private Button displayButton;

    @FXML
    public void initialize(){
        prgListView.setItems(getAllExamples());
        prgListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void displayProgram(ActionEvent action){
        IStatement selectedExample = prgListView.getSelectionModel().getSelectedItem();

        if (selectedExample == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("No example selected!");
            alert.showAndWait();
        }
        else {
            int id = prgListView.getSelectionModel().getSelectedIndex();
            try {
                selectedExample.typeCheck(new MyDictionary<>());
                ProgramState prg = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), selectedExample);
                IRepository repo = new Repository(prg, "log" + (id + 1) + ".txt");
                Controller ctr = new Controller(repo);
                executorController.setController(ctr);
            }
            catch (ADTException | StmtExecException | ExprEvalException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    public ObservableList<IStatement> getAllExamples(){
        List<IStatement> allExamples = new ArrayList<>();

        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("y", new IntType()),
                        new CompoundStatement(new AssignStatement("x", new ValueExpression(new IntValue(13))),
                                new CompoundStatement(new AssignStatement("y", new ValueExpression(new IntValue(10))),
                                        new IfStatement(new RelationalExpression("<", new VariableExpression("x"), new VariableExpression("y")), new PrintStatement(new VariableExpression("x")), new PrintStatement(new VariableExpression("y")))))));

        IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("filename", new StringType()),
                new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("ex5.in"))),
                        new CompoundStatement(new OpenReadFile(new VariableExpression("filename")),
                                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("filename"), "v"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("filename"), "v"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                        new CloseReadFile(new VariableExpression("filename"))))))))));

        IStatement ex6 = new CompoundStatement(new VariableDeclarationStatement("filename", new StringType()), new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
                new CompoundStatement(new AssignStatement("filename", new ValueExpression(new StringValue("ex6.in"))), new CompoundStatement(new AssignStatement("x", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new OpenReadFile(new VariableExpression("filename")), new CompoundStatement(new VariableDeclarationStatement("y", new IntType()),
                                new CompoundStatement(new ReadFile(new VariableExpression("filename"), "y"), new CompoundStatement(new CloseReadFile(new VariableExpression("filename")),
                                        new IfStatement(new RelationalExpression(">=", new VariableExpression("x"), new VariableExpression("y")), new PrintStatement(new VariableExpression("x")), new PrintStatement(new VariableExpression("y")))))))))));

        IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(3))), new CompoundStatement(new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v",
                        new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))), new PrintStatement(new VariableExpression("v")))));

        IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())), new CompoundStatement(new NewStatement("a",
                new ValueExpression(new IntValue(15))), new CompoundStatement(new VariableDeclarationStatement("b", new RefType(new RefType(new IntType()))),
                new CompoundStatement(new NewStatement("b", new VariableExpression("a")), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))),
                        new PrintStatement(new ArithmeticExpression('+', new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("b"))), new ValueExpression(new IntValue(5)))))))));

        IStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())), new CompoundStatement(new NewStatement("a",
                new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("b", new RefType(new RefType(new IntType()))),
                new CompoundStatement( new NewStatement("b", new VariableExpression("a")),  new CompoundStatement(new NewStatement("a",
                        new ValueExpression(new IntValue(30))), new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("b")))))))));

        IStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())), new CompoundStatement(new NewStatement("v",
                new ValueExpression(new IntValue(20))), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(40))), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(99))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v")))))));

        IStatement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

        IStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("counter", new IntType()), new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                new WhileStatement(new RelationalExpression("<", new VariableExpression("counter"), new ValueExpression(new IntValue(10))),
                        new CompoundStatement(new ForkStatement(new ForkStatement(new CompoundStatement(new NewStatement("a", new VariableExpression("counter")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))) )), new AssignStatement("counter",
                                new ArithmeticExpression('+', new VariableExpression("counter"), new ValueExpression(new IntValue(1))))))));

        allExamples.add(ex1);
        allExamples.add(ex2);
        allExamples.add(ex3);
        allExamples.add(ex4);
        allExamples.add(ex5);
        allExamples.add(ex6);
        allExamples.add(ex7);
        allExamples.add(ex8);
        allExamples.add(ex9);
        allExamples.add(ex10);
        allExamples.add(ex11);
        allExamples.add(ex12);

        return FXCollections.observableArrayList(allExamples);
    }
}
