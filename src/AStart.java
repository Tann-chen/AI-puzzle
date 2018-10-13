import java.util.*;
import java.util.function.ToIntFunction;

public class AStart extends BestFirst {
    private ToIntFunction<State> gFunction;

    public AStart(ToIntFunction hFunction) {
        super();
        this.heuristic = hFunction;
        this.gFunction = setGFunction();
        Comparator<State> priority = (s1, s2) -> (this.gFunction.applyAsInt(s2) + this.heuristic.applyAsInt(s2)) - (this.gFunction.applyAsInt(s1) + this.heuristic.applyAsInt(s1));
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
}
