import java.util.*;
import java.util.function.ToIntFunction;

public class BestFirst {
    protected Set<State> addedState;
    protected PriorityQueue<State> pQueue;
    protected ToIntFunction<State> heuristic;

    public BestFirst() {
        this.addedState = new HashSet<>();
    }

    public BestFirst(ToIntFunction<State> heuristic) {
        this();
        this.heuristic = heuristic;
        Comparator<State> priority = (s1, s2) -> this.heuristic.applyAsInt(s1) - this.heuristic.applyAsInt(s2);
        this.pQueue = new PriorityQueue<>(priority);
    }

    public void search(State initState) {
        addedState.add(initState);
        pQueue.offer(initState);
        int moveCounter = 0;

        while (!pQueue.isEmpty()) {
            State currState = pQueue.poll();
            moveCounter++;

            System.out.println(currState);

            if (Puzzle.isGoalState(currState)) {
                System.out.println("[INFO] find the goal state, moves :" + String.valueOf(moveCounter - 1));
                return;
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
        System.out.println("[INFO] can not find the goal state");
    }
}
