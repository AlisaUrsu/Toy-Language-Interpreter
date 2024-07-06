module com.example.a7v2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.a7v2 to javafx.fxml;
    exports com.example.a7v2;

    exports Controller;
    opens Controller to javafx.fxml;

    exports Exceptions;
    opens Exceptions to javafx.fxml;

    exports  Model.Expressions;
    opens Model.Expressions to javafx.fxml;

    exports Model.ProgramState;
    opens Model.ProgramState to javafx.fxml;

    exports Model.Statements;
    opens Model.Statements to javafx.fxml;

    exports Model.Type;
    opens Model.Type to javafx.fxml;

    exports Model.Collections;
    opens Model.Collections to javafx.fxml;

    exports Model.Value;
    opens Model.Value to javafx.fxml;

    exports Repository;
    opens Repository to javafx.fxml;
}