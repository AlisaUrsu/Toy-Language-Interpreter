package Repository;

import Exceptions.*;
import Model.ProgramState.ProgramState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramList();
    void addProgram(ProgramState program);
    void setProgramStates(List<ProgramState> states);
    void logPrgStateExecute(ProgramState prgState) throws IOException, ADTException;
    void emptyLogFile() throws IOException;
}