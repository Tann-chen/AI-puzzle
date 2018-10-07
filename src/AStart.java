import java.util.function.ToIntFunction;

public class AStart extends BestFirst {
    private ToIntFunction<State> gFunction;

    public AStart(ToIntFunction hFunction) {
        super(hFunction);
        this.gFunction = setGFunction();
    }

    private ToIntFunction<State> setGFunction() {
        return null;
    }
}
