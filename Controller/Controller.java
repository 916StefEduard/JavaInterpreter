package controller;
import exceptions.MyException;
import model.PrgState;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    IRepository repository;
    ExecutorService executorService;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public IRepository getRepository(){
        return this.repository;
    }

    public void addProgram(PrgState state) {
        repository.add(state);
    }

    Map<Integer, IValue> garbageCollector(List<Integer> addresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddresses(Collection<IValue> symTableValues, Collection<IValue> heapValues) {
        return Stream.concat(
                heapValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();}),
                symTableValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                ).collect(Collectors.toList());
    }

    void conservativeGarbageCollector(List<PrgState> prgStateList) {
        List<Integer> addresses = Objects.requireNonNull(prgStateList.stream()
                                    .map(prg -> getAddresses(prg.getSymTable().getContent().values(), prg.getHeapTable().getContent().values()))
                                    .map(Collection::stream)
                                    .reduce(Stream::concat).orElse(null)).collect(Collectors.toList());
        prgStateList.forEach(prg -> prg.getHeapTable().setContent(garbageCollector(
                addresses,
                prg.getHeapTable().getContent()
        )));
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgStateList) throws MyException {
        prgStateList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        });
        List<Callable<PrgState>> callableList = prgStateList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = null;
        try {
            newPrgList = executorService.invokeAll(callableList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (MyException  | InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                            System.exit(1);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        prgStateList.addAll(newPrgList);
        prgStateList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        });
        repository.setPrgList(prgStateList);
    }

    public void oneStepAll(){
        executorService = Executors.newFixedThreadPool(2);
        List<PrgState> prgStateList = removeCompletedPrg(repository.getPrgList());
        if(!prgStateList.isEmpty()){
            oneStepForAllPrg(prgStateList);
            prgStateList = removeCompletedPrg(repository.getPrgList());
        }else {
            executorService.shutdownNow();
            repository.setPrgList(prgStateList);
        }
    }

    public void setExecutor(){
        executorService = Executors.newFixedThreadPool(2);
    }

    public void shutExecutor(){
        executorService.shutdownNow();
    }

    public void setList(List<PrgState> prgStates){
        oneStepForAllPrg(prgStates);
    }

    public void allStep() throws MyException, IOException {
        setExecutor();
        var prgStateList = removeCompletedPrg(repository.getPrgList());
        while (!prgStateList.isEmpty()) {
           // conservativeGarbageCollector(prgStateList);
            oneStepForAllPrg(prgStateList);
            prgStateList = removeCompletedPrg(repository.getPrgList());
        }
        shutExecutor();
        repository.setPrgList(prgStateList);
    }
}
