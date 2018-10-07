import java.util.*;

public class Puzzle {

    public static void main(String[] args) {
        //read from cli
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] nums = line.split("\\s+");

        int[] initial = new int[nums.length];
        Set<Integer> numSet = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            int temp = Integer.parseInt(nums[i]);
            numSet.add(temp);
            initial[i] = temp;
        }
        // check duplication
        if (numSet.size() != initial.length) {
            System.out.println("there is duplicated numbers in array");
            return;
        }
        State initState = new State(initial);

        DepthFirst depthFirstSearch = new DepthFirst();
        depthFirstSearch.search(initState);
    }

    public static boolean isGoalState(State state) {
        int[] tiles = state.getTiles();
        boolean isGoal = true;
        for (int i = 0; i <= 10; i++) {
            if (tiles[i] != i + 1) {
                isGoal = false;
                break;
            }
        }
        return isGoal;
    }

    public static List<State> getPosMoves(State currState) {
        int[] currTiles = currState.getTiles();

        //get index of blank
        int idxOfBlank = -1;
        for (int i = 0; i < currTiles.length; i++) {
            if (currTiles[i] == 0) {
                idxOfBlank = i;
                break;
            }
        }
        int row = idxOfBlank / 4;
        int col = idxOfBlank % 4;

        //generate children
        List<State> possibleMoves = new ArrayList<>();

        if (row != 0) {//up
            int[] newTiles = Arrays.copyOfRange(currTiles, 0, currTiles.length);
            newTiles[row * 4 + col] = currTiles[row * 4 + col - 4];
            newTiles[row * 4 + col - 4] = 0;
            State newState = new State(newTiles);
            newState.setParent(currState);
            possibleMoves.add(newState);
        }

        if (row != 0 && col != 3) {//right up
            int[] newTiles = Arrays.copyOfRange(currTiles, 0, currTiles.length);
            newTiles[row * 4 + col] = currTiles[row * 4 + col - 3];
            newTiles[row * 4 + col - 3] = 0;
            State newState = new State(newTiles);
            newState.setParent(currState);
            possibleMoves.add(newState);
        }

        if (col != 3) {//right
            int[] newTiles = Arrays.copyOfRange(currTiles, 0, currTiles.length);
            newTiles[row * 4 + col] = currTiles[row * 4 + col + 1];
            newTiles[row * 4 + col + 1] = 0;
            State newState = new State(newTiles);
            newState.setParent(currState);
            possibleMoves.add(newState);
        }

        if (row != 2 && col != 3) {//down right
            int[] newTiles = Arrays.copyOfRange(currTiles, 0, currTiles.length);
            newTiles[row * 4 + col] = currTiles[row * 4 + col + 5];
            newTiles[row * 4 + col + 5] = 0;
            State newState = new State(newTiles);
            newState.setParent(currState);
            possibleMoves.add(newState);
        }

        if (row != 2) {//down
            int[] newTiles = Arrays.copyOfRange(currTiles, 0, currTiles.length);
            newTiles[row * 4 + col] = currTiles[row * 4 + col + 4];
            newTiles[row * 4 + col + 4] = 0;
            State newState = new State(newTiles);
            newState.setParent(currState);
            possibleMoves.add(newState);
        }

        if (row != 2 && col != 0) {//down left
            int[] newTiles = Arrays.copyOfRange(currTiles, 0, currTiles.length);
            newTiles[row * 4 + col] = currTiles[row * 4 + col + 3];
            newTiles[row * 4 + col + 3] = 0;
            State newState = new State(newTiles);
            newState.setParent(currState);
            possibleMoves.add(newState);
        }

        if (col != 0) {//left
            int[] newTiles = Arrays.copyOfRange(currTiles, 0, currTiles.length);
            newTiles[row * 4 + col] = currTiles[row * 4 + col - 1];
            newTiles[row * 4 + col - 1] = 0;
            State newState = new State(newTiles);
            newState.setParent(currState);
            possibleMoves.add(newState);
        }

        if (row != 0 && col != 0) {//left up
            int[] newTiles = Arrays.copyOfRange(currTiles, 0, currTiles.length);
            newTiles[row * 4 + col] = currTiles[row * 4 + col - 5];
            newTiles[row * 4 + col - 5] = 0;
            State newState = new State(newTiles);
            newState.setParent(currState);
            possibleMoves.add(newState);
        }

        return possibleMoves;
    }
}
