package com.example.a7v2;

import Controller.Controller;
import Exceptions.ADTException;
import Exceptions.ExprEvalException;
import Exceptions.StmtExecException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Value.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ProgramExecutionController {
    private Controller controller;

    @FXML
    private TextField nrOfPrgStatesTextField;

    @FXML
    private TableView<Pair<Integer, Value>> heapTable;
    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> addressColumn;
    @FXML
    private TableColumn<Pair<Integer, Value>, String> valueColumn;

    @FXML
    private ListView<String> outList;

    @FXML
    private ListView<String> fileTableList;

    @FXML
    private ListView<Integer> prgStatesIDsList;

    @FXML
    private TableView<Pair<String, Value>> symTable;
    @FXML
    private TableColumn<Pair<String, Value>, String> variableNameColumn;
    @FXML
    private TableColumn<Pair<String, Value>, String> variableValueColumn;

    @FXML
    private ListView<String> exeStackList;
    @FXML
    private Button oneStepButton;

    public void setController(Controller ctr){
        controller = ctr;
        populate();
    }

    @FXML
    public void initialize(){
        nrOfPrgStatesTextField.setEditable(false);
        prgStatesIDsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

        variableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        variableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

    }

    public ProgramState getCurrPrgState(){
        if (controller.getPrgStates().isEmpty()){
            return null;
        }

        else {
            int currID = prgStatesIDsList.getSelectionModel().getSelectedIndex();
            if(currID == -1)
                return controller.getPrgStates().get(0);
            else
                return controller.getPrgStates().get(currID);
        }
    }

    public void populate(){
        populateHeapTableView();
        populateOutputListView();
        populateFileTableListView();
        populateSymbolTableView();
        populateExeStackListView();
        populatePrgStateIDsListView();
    }

    @FXML
    private void changeProgramState(MouseEvent event) {
        populateExeStackListView();
        populateSymbolTableView();
    }

    public void populateNrOfPrgStatesTextField(){
        nrOfPrgStatesTextField.setText(String.valueOf(controller.getPrgStates().size()));
    }

    public void populateHeapTableView(){
        ProgramState prgState = getCurrPrgState();
        ArrayList<Pair<Integer, Value>> heapEntries = new ArrayList<>();
        prgState.getHeap().getHeapContent().forEach((key, value) -> heapEntries.add(new Pair<>(key, value)));
        heapTable.setItems(FXCollections.observableArrayList(heapEntries));
    }

    public void populateOutputListView(){
        ProgramState prgState = getCurrPrgState();
        List<String> output = new ArrayList<>();
        prgState.getOut().getList().forEach(e -> output.add(e.toString()));
        outList.setItems(FXCollections.observableArrayList(output));
    }

    public void populateFileTableListView(){
        ProgramState prgState = getCurrPrgState();
        List<String> files = new ArrayList<>();
        prgState.getFileTable().getDictContent().keySet().forEach(key -> files.add(key));
        fileTableList.setItems(FXCollections.observableList(files));
    }

    public void populateSymbolTableView(){
        ProgramState prgState = getCurrPrgState();
        ArrayList<Pair<String, Value>> symTblEntries = new ArrayList<>();
        prgState.getSymTable().getDictContent().forEach((key, value) -> symTblEntries.add(new Pair<>(key, value)));
        symTable.setItems(FXCollections.observableArrayList(symTblEntries));
    }

    public void populateExeStackListView(){
        ProgramState prgState = getCurrPrgState();
        Stack<IStatement> stack = prgState.getExeStack().getStack();
        List<String> exeStackEntries = new ArrayList<>();
        ListIterator<IStatement> stackIterator = stack.listIterator(stack.size());
        while(stackIterator.hasPrevious())
            exeStackEntries.add(stackIterator.previous().toString());

        exeStackList.setItems(FXCollections.observableList(exeStackEntries));
    }

    public void populatePrgStateIDsListView(){
        List<ProgramState> prgStates = controller.getPrgStates();
        List<Integer> IDList = prgStates.stream().map(ProgramState::getId).collect(Collectors.toList());
        prgStatesIDsList.setItems(FXCollections.observableList(IDList));
        populateNrOfPrgStatesTextField();
    }

    @FXML
    private void runOneStep(MouseEvent mouseEvent) {
        if (controller != null) {
            try {
                List<ProgramState> programStates = Objects.requireNonNull(controller.getPrgStates());
                if (!programStates.isEmpty()) {
                    controller.oneStep();
                    populate();
                    programStates = controller.removeCompletedPrg(controller.getPrgStates());
                    controller.setPrgStates(programStates);
                    populatePrgStateIDsListView();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("An error has occurred!");
                    alert.setContentText("There is nothing left to execute!");
                    alert.showAndWait();
                }
            } catch (StmtExecException | ADTException | ExprEvalException | IOException | InterruptedException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Execution error!");
                alert.setHeaderText("An execution error has occurred!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText("No program selected!");
            alert.showAndWait();
        }
    }
}
