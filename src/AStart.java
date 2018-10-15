import java.util.*;
import java.util.function.ToIntFunction;

public class AStart extends BestFirst {
    private ToIntFunction<State> gFunction;

    public AStart(ToIntFunction hFunction) {
        super();
        this.heuristic = hFunction;
        this.gFunction = setGFunction();
        Comparator<State> priority = (s1, s2) -> (this.gFunction.applyAsInt(s1) + this.heuristic.applyAsInt(s1)) - (this.gFunction.applyAsInt(s2) + this.heuristic.applyAsInt(s2));
        this.pQueue = new PriorityQueue<>(priority);
    }

    private ToIntFunction<State> setGFunction() {
        return new ToIntFunction<State>() {
            @Override
            public int applyAsInt(State value) {
                int cost = 0;
                State current = value;
                while (current.getParent() != null){
                    current = current.getParent();
                    cost++;
                }
                return cost;
            }
        };
    }

    @Override
    public void search(State initState, String outputFile) {
        addedState.add(initState);
        pQueue.offer(initState);
        int moveCounter = 0;

        while (!pQueue.isEmpty()) {
            State currState = pQueue.poll();
            moveCounter++;

            System.out.println(currState);

            if (Puzzle.isGoalState(currState)) {
                System.out.println("[INFO] find the goal state, Search path moves :" + String.valueOf(moveCounter - 1));
                Puzzle.outputSolutionPath(currState, "puzzleAS-" + outputFile + ".txt");
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
