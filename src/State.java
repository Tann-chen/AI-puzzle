import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class State {
    private int[] tiles;
    private State parent;
    private List<State> children;

    public State(int[] tiles) {
        this.tiles = tiles;
    }

    public int[] getTiles() {
        return tiles;
    }

    public void setTiles(int[] tiles) {
        this.tiles = tiles;
    }

    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public List<State> getChildren() {
        return children;
    }

    public void setChildren(List<State> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (this.tiles.length != state.tiles.length) return false;

        boolean isEqual = true;

        for (int i = 0; i < this.tiles.length; i++) {
            if (this.tiles[i] != state.tiles[i]) {
                isEqual = false;
                break;
            }
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tiles);
    }

    @Override
    public String toString() {
        return "[" + Arrays.stream(this.tiles).mapToObj(String::valueOf).collect(Collectors.joining(",")) + "]";
    }
}
