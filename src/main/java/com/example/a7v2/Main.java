package com.example.a7v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage listStage) throws IOException {
        FXMLLoader prgListLoader = new FXMLLoader(Main.class.getResource("ProgramListController.fxml"));
        Scene prgListScene = new Scene(prgListLoader.load(), 765, 500);
        ProgramListController prgListCtr = prgListLoader.getController();
        listStage.setTitle("Select a program!");
        listStage.setScene(prgListScene);
        listStage.show();

        FXMLLoader prgExecLoader = new FXMLLoader(Main.class.getResource("ProgramExecutionController.fxml"));
        Scene prgExecScene = new Scene(prgExecLoader.load(), 910, 620);
        ProgramExecutionController prgExecCtr = prgExecLoader.getController();
        prgListCtr.setExecutorController(prgExecCtr);
        Stage execStage = new Stage();
        execStage.setTitle("Toy Language Interpreter");
        execStage.setScene(prgExecScene);
        execStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}