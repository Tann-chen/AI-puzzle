import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.ToIntFunction;

public class BestFirst {
    public Set<State> addedState;
    public PriorityQueue<State> pQueue;
    public ToIntFunction<State> heuristic;

    public BestFirst(ToIntFunction<State> heuristic) {
        this.addedState = new HashSet<>();
        this.heuristic = heuristic;
        this.pQueue = new PriorityQueue<>(Comparator.comparingInt(this.heuristic));
    }

    public void search(State initState) {
        addedState.add(initState);

    }


}
