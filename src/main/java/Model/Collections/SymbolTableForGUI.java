package Model.Collections;

import Model.Value.Value;
import javafx.beans.property.SimpleStringProperty;

public class SymbolTableForGUI {
    private SimpleStringProperty variableName;
    private SimpleStringProperty value;


    public SymbolTableForGUI(String vn, Value v){
        variableName = new SimpleStringProperty(vn);
        value = new SimpleStringProperty(v.toString());
    }

    public SimpleStringProperty variableNameProperty(){
        return variableName;
    }

    public SimpleStringProperty valueProperty(){
        return value;
    }

    public String getVariableName(){
        return variableName.get();
    }

    public String getValue(){
        return this.value.get();
    }

    public void setVariableName(String newVariableName){
        variableName.set(newVariableName);
    }

    public void setValue(String newValue){
        value.set(newValue);
    }

}
