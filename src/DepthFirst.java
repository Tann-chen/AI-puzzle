import java.util.*;

public class DepthFirst {
    public Set<State> addedState;
    public Stack<State> stack;


    public DepthFirst() {
        this.addedState = new HashSet<>();
        this.stack = new Stack<>();
    }

    public void search(State initState) {
        addedState.add(initState);
        stack.add(initState);

        while (!stack.empty()) {
            State currState = stack.pop();

            System.out.println(currState);

            if (Puzzle.isGoalState(currState)) {
                break;
            }
            // check its children
            List<State> possibleMoves = Puzzle.getPosMoves(currState);
            List<State> children = new ArrayList<>();

            for (int i = possibleMoves.size()-1; i >=0 ; i--) {
                State pm = possibleMoves.get(i);
                if (!addedState.contains(pm)) {
                    children.add(pm);
                    addedState.add(pm);
                    stack.add(pm);
                }
            }

            currState.setChildren(children);
        }

        // fail to find goal
        System.out.println("[INFO] can not find the goal!");
    }
}
