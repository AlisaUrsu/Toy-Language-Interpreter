package Controller;

import Exceptions.*;
import Model.Expressions.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.*;
import Model.Value.*;
import Model.Collections.*;
import Model.Statements.IStatement;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller{
    private IRepository repository;
    private boolean displayFlag;
    private ExecutorService executor;

    public Controller(IRepository repo){
        repository = repo;
        displayFlag = false;
    }

    public void setDisplayFlag(boolean value){
        displayFlag = value;
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> garbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void conservativeGarbageCollector(List<ProgramState> prgStates) {
        List<Integer> symTblAddrs = prgStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null)
                .collect(Collectors.toList());
        prgStates.forEach(p -> {
            p.getHeap().setHeap((HashMap<Integer, Value>) garbageCollector(symTblAddrs, getAddrFromHeap(p.getHeap().getHeapContent().values()), p.getHeap().getHeapContent()));
        });
    }


    private void display(ProgramState programState) throws ADTException {
        if (displayFlag) {
            System.out.println(programState.PrgmStateToString());
        }
    }

    public void oneStepForAllPrg(List<ProgramState> prgList) throws IOException, ADTException, InterruptedException {
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExecute(prg);
                display(prg);
            } catch (IOException | ADTException e) {
                System.out.println(e.getMessage());
            }
        });
        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try{
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExecute(prg);
                display(prg);
            } catch (IOException | ADTException e) {
                System.out.println(e.getMessage());
            }
        });

        repository.setProgramStates(prgList);

    }

    public void oneStep() throws StmtExecException, ADTException, ExprEvalException, IOException, InterruptedException {
        executor= Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrg(repository.getProgramList());
        oneStepForAllPrg(programStates);
        conservativeGarbageCollector(programStates);
        executor.shutdownNow();
    }

    public void allStep() throws StmtExecException, ADTException, ExprEvalException, IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = repository.getProgramList();
        while (prgList.size() > 0){
            oneStepForAllPrg(prgList);
            conservativeGarbageCollector(prgList);
            prgList = removeCompletedPrg(repository.getProgramList());
        }
        executor.shutdownNow();
        repository.setProgramStates(prgList);
    }



    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList){
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public List<ProgramState> getPrgStates(){
        return repository.getProgramList();
    }

    public void setPrgStates(List<ProgramState> prgStates){
        repository.setProgramStates(prgStates);
    }

}