package Controller;
import Model.PrgState;
import Model.adt.IHeap;
import Model.value.IValue;
import Model.value.RefValue;
import Repo.Repo;
import Exception.*;
import Exception.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    Repo repo;
    ExecutorService executor;

    public Controller(Repo repo){
        this.repo = repo;
    }

    public Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, HashMap<Integer,IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues, IHeap<Integer,IValue> heap){
        List<Integer> result = new LinkedList<>();
        symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .forEach(v -> {
                    RefValue value = (RefValue) v;
                    result.add(value.getHeadAddress());
                    IValue heapValue = null;
                    heapValue = heap.lookup(value.getHeadAddress());
                    while(heapValue instanceof RefValue){
                        result.add(((RefValue)heapValue).getHeadAddress());
                        heapValue = heap.lookup(((RefValue)heapValue).getHeadAddress());
                    }
                });
                return result;
    }

    public Repo getRepository(){
        return this.repo;
    }

    public void addProgram(PrgState newPrg) {
        repo.addPrg(newPrg);
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(ArrayList<PrgState> prgList) throws InterruptedException {
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future-> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException | ClassCastException e) {
                        e.printStackTrace();
                    }
                   return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
            } catch (StackException | IOException e) {
                e.printStackTrace();
            }
        });
        repo.setPrgList(prgList);
    }

    public void allStep() throws InterruptedException, RepositoryException {
        executor = Executors.newFixedThreadPool(2);
        var prgList = removeCompletedPrg(repo.getMyPrgStates());
        while(prgList.size() > 0){
            this.oneStepForAllPrg((ArrayList<PrgState>)prgList);
            var program = repo.getPrgList();
            program.getHeap().setContent(
                    (HashMap<Integer, IValue>) unsafeGarbageCollector(
                            getAddrFromSymTable(program.getSymTable().getDictionary().values(),
                            program.getHeap()),
                            program.getHeap().getHashMap()
                    )
            );
            prgList = removeCompletedPrg(repo.getMyPrgStates());
        }
        var output = repo.getOutput();
        System.out.println("Output:" + output);
        executor.shutdownNow();
        repo.setPrgList((ArrayList<PrgState>) prgList);
    }
}
