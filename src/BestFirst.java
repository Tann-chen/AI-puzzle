import java.util.*;
import java.util.function.ToIntFunction;

public class BestFirst {
    private Set<State> addedState;
    private PriorityQueue<State> pQueue;
    private ToIntFunction<State> heuristic;

    public BestFirst(ToIntFunction<State> heuristic) {
        this.addedState = new HashSet<>();
        this.heuristic = heuristic;
        Comparator<State> priority = (s1, s2) -> this.heuristic.applyAsInt(s2) - this.heuristic.applyAsInt(s1);
        this.pQueue = new PriorityQueue<>(priority);
    }

    public void search(State initState) {
        addedState.add(initState);
        pQueue.offer(initState);

        while (!pQueue.isEmpty()) {
            State currState = pQueue.poll();

            if (Puzzle.isGoalState(currState)) {
                break;
            }
            // check its children
            List<State> possibleMoves = Puzzle.getPosMoves(currState);
            List<State> children = new ArrayList<>();
            for (State s : possibleMoves) {
                if (!addedState.contains(s)) {
                    addedState.add(s);
                    pQueue.offer(s);
                    children.add(s);
                }
            }
            currState.setChildren(children);
        }

        // fail to find goal
        System.out.println("[INFO] can not find the goal!");
    }
}
