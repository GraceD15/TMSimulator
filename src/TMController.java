import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TMController {
    /* Rules and the Like
    * States must have unique names, names are final
    * */

    private final TreeMap<String, TMState> states;
    private TMTape tape;
    private TMState initial;
    private TMState accepting;
    private TMState rejecting;
    private final Set<String> breakpoints = new TreeSet<>();
    private TMState current;

    public TMController() {
        states = new TreeMap<>();
    }

    public TMController(String s) {
        states = new TreeMap<>();
        String[] a = s.split("~");
        for (String substr : a) {
            String[] info = substr.split("\\|");
            TMState st = new TMState(info[0]);
            for (int i = 1; i < info.length; i++) {
                st.addTransition(new TMTransition(info[i]));
            }
            switch (info[0]) {
                case "qa" -> this.addAcceptingState(st);
                case "qr" -> this.addRejectingState(st);
                case "q0" -> this.addInitialState(st);
                default -> this.addState(st);
            }
        }
    }

    public void addTape(TMTape tape) {
        this.tape = tape;
    }

    public void reset() {
        current = initial;
    }

    public void addBreakPoint(String s) {
        breakpoints.add(s);
    }

    public void addState(TMState state) {
        states.put(state.getName(), state);
    }

    public void addInitialState(TMState state) {
        states.put(state.getName(), state);
        initial = state;
        current = state;
    }

    public void addAcceptingState(TMState state) {
        states.put(state.getName(), state);
        accepting = state;
    }

    public void addRejectingState(TMState state) {
        states.put(state.getName(), state);
        rejecting = state;
    }

    public TMState getState(String s) {
        return states.get(s);
    }

    // 0 = accept, 1 = reject, 2 = keep going
    public int step() {
        TMTransition t = current.getTransition(tape.getChar());
        if (t == null) return 1;
        tape.setChar(t.getReplace());

        if (tape.movePointer(t.getMoveRight()) == 1) {
            return 1;
        }
        TMState next = states.get(t.getNextStateName());
        if (next != null) {
            current = next;
            if (current.equals(accepting)) {
                return 0;
            } else if (current.equals(rejecting)) {
                return 1;
            }
        } else {
            return 1;
        }
        return 2;
    }

    public int fastForward() {
        int out = step();
        while (out == 2) {
            if (breakpoints.contains(current.getName())) {
                return 2;
            }
            out = step();
        }
        if (out == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    public String getStatesAsString() {
        return states.values().toString();
    }

    public String getStatesAndTransitionsAsString() {
        StringBuilder sb = new StringBuilder();
        for (TMState s : states.values()) {
            sb.append(s).append("\t:");
            for (TMTransition t : s.getTransitions()) {
                sb.append("\t").append(t);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getCode() {
        StringBuilder sb = new StringBuilder();
        for (TMState s : states.values()) {
            sb.append(s).append("|");
            for (TMTransition t : s.getTransitions()) {
                sb.append(t).append("|");
            }
            sb.append("~");
        }
        return sb.toString();
    }

    public Set<String> getBreakpoints() {
        return breakpoints;
    }

    @Override
    public String toString() {
        return tape.toString() + "\n" + "state " + current;
    }
}


