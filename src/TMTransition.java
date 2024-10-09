import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TMTransition {
    private final char read;
    private final char replace; // same as read if no overwriting
    private final boolean moveRight;
    private final String nextStateName;

    public TMTransition(Character read, Character replace, boolean moveRight, String nextStateName) {
        this.read = read;
        this.replace = replace;
        this.moveRight = moveRight;
        this.nextStateName = nextStateName;
    }

    static final Pattern withReplace = Pattern.compile("([a-zA-Z0-9_#])/([a-zA-Z0-9_#]);([LR]) -> ([a-zA-Z0-9_]+)");
    static final Pattern withoutReplace = Pattern.compile("([a-zA-Z0-9_#]);([LR]) -> ([a-zA-Z0-9_]+)");

    public TMTransition(String tString) {
        Matcher matcherWR = withReplace.matcher(tString.trim());
        Matcher matcherWOR = withoutReplace.matcher(tString.trim());
        if (matcherWR.matches()) {
            read = matcherWR.group(1).charAt(0);
            replace = matcherWR.group(2).charAt(0);
            moveRight = matcherWR.group(3).equals("R");
            nextStateName = matcherWR.group(4);
        } else if (matcherWOR.matches()){
            read = matcherWOR.group(1).charAt(0);
            replace = read;
            moveRight = matcherWOR.group(2).equals("R");
            nextStateName = matcherWOR.group(3);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public char getRead() {
        return read;
    }

    public char getReplace() {
        return replace;
    }

    public boolean getMoveRight() {
        return moveRight;
    }

    public String getNextStateName() {
        return nextStateName;
    }

    @Override
    public String toString() {
        if (read == replace) {
            return read + ";" + (moveRight ? "R" : "L") + " -> " + nextStateName;
        } else {
            return read + "/" + replace + ";" + (moveRight ? "R" : "L") + " -> " + nextStateName;
        }
    }
}
