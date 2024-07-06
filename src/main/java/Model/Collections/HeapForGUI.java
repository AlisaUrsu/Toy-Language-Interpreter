package Model.Collections;

import Model.Value.Value;
import javafx.beans.property.SimpleStringProperty;

public class HeapForGUI {
    private SimpleStringProperty heapAddress;
    private SimpleStringProperty heapValue;

    public HeapForGUI(Integer ha, Value hv) {
        heapAddress = new SimpleStringProperty(Integer.toString(ha));
        heapValue = new SimpleStringProperty(hv.toString());
    }

    public SimpleStringProperty heapAddressProperty() {
        return heapAddress;
    }

    public SimpleStringProperty heapValueProperty() {
        return heapValue;
    }

    public String getHeapAddress() {
        return heapAddress.get();
    }

    public String getHeapValue() {
        return heapValue.get();
    }

    public void setHeapAddress(Integer newHeapAddress) {
        heapAddress.set(String.valueOf(newHeapAddress));
    }

    public void setHeapValue(Value newHeapValue) {
        heapValue.set(newHeapValue.toString());
    }
}
