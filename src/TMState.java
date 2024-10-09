import java.util.Objects;
import java.util.Collection;
import java.util.TreeMap;

public class TMState {
    private final String name;
    private TreeMap<Character, TMTransition> transitions;

    public TMState(String name) {
        this.name = name;
        transitions = new TreeMap<>();
    }

    public String getName() {
        return name;
    }

    public void addTransition(TMTransition t) {
        transitions.put(t.getRead(), t);
    }

    public TMTransition getTransition(char c) {
        return transitions.get(c);
    }

    public Collection<TMTransition> getTransitions() {
        return transitions.values();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TMState tmState = (TMState) o;
        return Objects.equals(name, tmState.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
