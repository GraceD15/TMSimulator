import java.util.ArrayList;
import java.util.Arrays;

public class TMTape {
    private final ArrayList<Character> tape;
    private int pointer;

    public TMTape(String s) {
        tape = new ArrayList<>();
        for (char c : s.toCharArray()) {
            tape.add(c);
        }
        pointer = 0;
    }

    public char getChar() {
        return tape.get(pointer);
    }

    public void setChar(char c) {
        tape.set(pointer,c);
    }

    public int movePointer(boolean moveRight) {
        if (moveRight) {
            pointer ++;
            if (pointer >= tape.size()) {
                tape.add('_');
            }
            return 0;
        } else {
            if (pointer > 0) {
                pointer --;
                return 0;
            } else {
                return 1;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < tape.size(); i++) {
            sb1.append(i).append(" ");
            sb2.append(tape.get(i)).append(" ");
        }
        sb2.append("\n");
        sb2.append("  ".repeat(Math.max(0, pointer)));
        sb2.append("^");
        sb1.append("\n").append(sb2);
        return sb1.toString();
    }
}
