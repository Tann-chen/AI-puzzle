import java.util.*;
import java.util.function.ToIntFunction;

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

//        DepthFirst depthFirstSearch = new DepthFirst();
//        depthFirstSearch.search(initState);

//        BestFirst bfH1 = new BestFirst(heuristic1());
//        bfH1.search(initState);
//
//        AStart asH1 = new AStart(heuristic1());
//        asH1.search(initState);


//        BestFirst bfH2 = new BestFirst(heuristic2());
//        bfH2.search(initState);
//
//        AStart asH2 = new AStart(heuristic2());
//        asH2.search(initState);

        BestFirst bfH3 = new BestFirst(heuristic3());
        bfH3.search(initState);

        AStart asH3 = new AStart(heuristic3());
        asH3.search(initState);
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


    public static ToIntFunction<State> heuristic1() {
        return new ToIntFunction<State>() {
            @Override
            public int applyAsInt(State value) {
                int[] tiles = value.getTiles();
                int sumDistance = 0;
                for (int i = 0; i < 11; i++) {
                    int currCol = i % 4;
                    int currRow = i / 4;
                    int targCol = (tiles[i] - 1) % 4;
                    int targRow = (tiles[i] - 1) / 4;
                    int movesToTarg = Math.max(Math.abs(targCol - currCol), Math.abs(targRow - currRow)); //see the report
                    sumDistance = sumDistance + movesToTarg;
                }

                return sumDistance;
            }
        };
    }


    public static ToIntFunction<State> heuristic2() {
        return new ToIntFunction<State>() {
            @Override
            public int applyAsInt(State value) {
                int[] tiles = value.getTiles();
                int sumCount = 0;

                for (int i = 0; i <= 11; i++) {
                    int flag = tiles[i];
                    int count = 0;
                    for (int j = i + 1; j <= 11; j++) {
                        if (tiles[j] > flag) {
                            count++;
                        }
                    }
                    sumCount = sumCount + count;
                }
                return sumCount;
            }
        };
    }

    public static ToIntFunction<State> heuristic3() {
        return new ToIntFunction<State>() {
            @Override
            public int applyAsInt(State value) {
                int[] tiles = value.getTiles();
                int val = 0;

                //check rows
                for (int i = 0; i <= 2; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (tiles[i * 4 + j] > tiles[i * 4 + j + 1]) {
                            val++;
                            break;
                        }
                    }
                }

                //check cols
                for (int j = 0; j <= 3; j++) {
                    for (int i = 0; i < 2; i++) {
                        if (tiles[i * 4 + j] > tiles[i * 4 + 4 + j]) {
                            val++;
                            break;
                        }
                    }
                }

                return val;
            }
        };
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
