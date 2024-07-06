package Repository;

import Exceptions.ADTException;
import Exceptions.StmtExecException;
import Model.ProgramState.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<ProgramState> programStates;
    private int currentPosition;
    private String logFilePath;

    public Repository(ProgramState ps, String log){
        programStates = new ArrayList<>();
        currentPosition = 0;
        logFilePath = log;
        addProgram(ps);
        try {
            emptyLogFile();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /*public int getCurrentPosition(){
        return currentPosition;
    }

    public void setCurrentPosition(int current){
        currentPosition = current;
    }*/

    @Override
    public List<ProgramState> getProgramList() {
        return programStates;
    }

    @Override
    public void addProgram(ProgramState program) {
        programStates.add(program);
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates = programStates;
    }


    @Override
    public void logPrgStateExecute(ProgramState prgState) throws IOException, ADTException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))   ;
        logFile.println(prgState.PrgmStateToString());
        logFile.close();
    }

    @Override
    public void emptyLogFile() throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.close();
    }


}
