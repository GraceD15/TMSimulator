import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Welcome to the Turing Machine Simulator!");
        System.out.println("Press enter to start.");
        s.nextLine();
        TMController tm;
        System.out.println("Build the Turing Machine:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Choose your build method. Y - TM code. N - Manual");
        if (getYesNo(s)) {
            while (true) {
                System.out.println("Enter TM code:");
                String regex = "([a-zA-z0-9_]+|.*~)+";
                Pattern TMCode = Pattern.compile(regex);
                String tmCode = s.nextLine();
                Matcher match = TMCode.matcher(tmCode);
                if (match.matches()) {
                    tm = new TMController(tmCode);
                    break;
                } else {
                    System.out.println("Invalid code.");
                }
            }
            System.out.println(tm.getStatesAndTransitionsAsString());

        } else {
            tm = new TMController();
            tm.addInitialState(new TMState("q0"));
            tm.addAcceptingState(new TMState("qa"));
            tm.addRejectingState(new TMState("qr"));

            System.out.println("Three states have been automatically created: " +
                    "q0 is the initial state, qa is the accepting state, and qr is the rejecting state.");

            String statesRegex = "([A-Za-z0-9_]+)(, [A-Za-z0-9_]+)*";
            String statesRangeRegex = "(\\d+)->(\\d+)";
            Pattern statesPattern = Pattern.compile(statesRegex);
            Pattern statesRangePattern = Pattern.compile(statesRangeRegex);
            System.out.println("States: " + tm.getStatesAsString());
            System.out.println("States must have unique names. Add states? (Y/N)");

            boolean addState = getYesNo(s);
            while (addState) {
                System.out.println("Input states comma-separated in the format 'q1, q2, q3' or as a range '1->3':");
                String statesInput = s.nextLine();
                Matcher statesMatcher = statesPattern.matcher(statesInput);
                Matcher rangeMatcher = statesRangePattern.matcher(statesInput);
                if (statesMatcher.matches()) {
                    for (String sub : statesMatcher.group(0).split(",")) {
                        String tryName = sub.trim();
                        if (tm.getState(tryName) == null) {
                            tm.addState(new TMState(tryName));
                        } else {
                            System.out.println("'" + tryName + "' already exists. Not added.");
                        }
                    }
                    System.out.println("States: " + tm.getStatesAsString());
                    System.out.println("Add more states? (Y/N)");
                    addState = getYesNo(s);
                } else if (rangeMatcher.matches()) {
                    int lo = Integer.parseInt(rangeMatcher.group(1));
                    int hi = Integer.parseInt(rangeMatcher.group(2));
                    if (lo < hi && lo > 0) {
                        for (int i = lo; i <= hi; i++) {
                            if (tm.getState("q" + i) == null) {
                                tm.addState(new TMState("q" + i));
                            } else {
                                System.out.println("'" + "q" + i + "' already exists. Not added.");
                            }
                        }
                        System.out.println("States: " + tm.getStatesAsString());
                        System.out.println("Add more states? (Y/N)");
                        addState = getYesNo(s);
                    } else {
                        System.out.println(lo + " to " + hi + " is an invalid range.");
                    }
                } else {
                    System.out.println("Invalid format.");
                }
            }

            String WRRegex = "([a-zA-Z0-9_]+)->([a-zA-Z0-9_#](,[a-zA-Z0-9_#])*)/([a-zA-Z0-9_#]);([LR])->([a-zA-Z0-9_]+)";
            String WORRegex = "([a-zA-Z0-9_]+)->([a-zA-Z0-9_#](,[a-zA-Z0-9_#])*);([LR])->([a-zA-Z0-9_]+)";
            Pattern withReplace = Pattern.compile(WRRegex);
            Pattern withoutReplace = Pattern.compile(WORRegex);

            System.out.println("Enter the transition in the format q0->a,b/c;R->qa or q0->a;L->qr with no spaces. " +
                    "\nTo finish adding transitions type 'save'. To view current transitions type 'view'." +
                    "To clear transitions from a given state type 'clear [state_name]'");

            while (true) {
                System.out.print(">");
                String tString = s.nextLine();
                Matcher matcherWR = withReplace.matcher(tString);
                Matcher matcherWOR = withoutReplace.matcher(tString);

                if (matcherWR.matches()) {
                    String stateName = matcherWR.group(1);
                    TMState state = tm.getState(stateName);
                    String reads = matcherWR.group(2);
                    char replace = matcherWR.group(4).charAt(0);
                    boolean moveRight = matcherWR.group(5).equals("R");
                    String nextState = matcherWR.group(6);
                    if (state == null) {
                        System.out.println("'" + stateName + "' is not a valid state name. Try again");
                        continue;
                    }
                    if (tm.getState(nextState) == null) {
                        System.out.println("'" + nextState + "' is not a valid state name. Try again");
                        continue;
                    }
                    for (int i = 0; i < reads.length(); i+=2) {
                        state.addTransition(new TMTransition(reads.charAt(i), replace, moveRight,nextState));
                    }
                } else if (matcherWOR.matches()) {
                    String stateName = matcherWOR.group(1);
                    TMState state = tm.getState(stateName);
                    String reads = matcherWOR.group(2);
                    boolean moveRight = matcherWOR.group(4).equals("R");
                    String nextState = matcherWOR.group(5);
                    if (state == null) {
                        System.out.println("'" + stateName + "' is not a valid state name. Try again");
                        continue;
                    }
                    if (tm.getState(nextState) == null) {
                        System.out.println("'" + nextState + "' is not a valid state name. Try again");
                        continue;
                    }
                    for (int i = 0; i < reads.length(); i+=2) {
                        state.addTransition(new TMTransition(reads.charAt(i), reads.charAt(i), moveRight,nextState));
                    }
                } else if (tString.trim().equals("save")) {
                    break;
                } else if (tString.trim().equals("view")) {
                    System.out.println(tm.getStatesAndTransitionsAsString());
                } else if (tString.trim().contains("clear")) {
                    String clearState = tString.trim().split(" ")[1];
                    if (tm.getState(clearState) != null) {
                        tm.getState(clearState).getTransitions().clear();
                        System.out.println(clearState + " cleared.");
                    } else {
                        System.out.println("Not a state");
                    }
                } else {
                    System.out.println("Not formatted correctly. Try again.");
                }
            }
        }


        boolean addInput = true;
        while (addInput) {
            System.out.println("Enter the TM input string:");
            String input = s.nextLine();
            tm.addTape(new TMTape(input));
            tm.reset();
            System.out.println(tm);
            tm.getBreakpoints().clear();
            System.out.println("Set breakpoints? (Y/N)");
            if (getYesNo(s)) {
                System.out.println("Type states to set as breakpoints in format: 'q0, q1, q2'");
                String statesInput = s.nextLine();
                Matcher statesMatcher = Pattern.compile("([A-Za-z0-9_]+)(, [A-Za-z0-9_]+)*").matcher(statesInput);
                if (statesMatcher.matches()) {
                    for (String sub : statesMatcher.group(0).split(",")) {
                        String tryName = sub.trim();
                        if (tm.getState(tryName) != null) {
                            tm.addBreakPoint(tryName);
                        } else {
                            System.out.println("'" + tryName + "' is not a state. Not added.");
                        }
                    }
                    System.out.println("Breakpoints: " + tm.getBreakpoints());
                }
            }

            System.out.println("Press enter to step. Type 'ff' to fast forward to the end or next breakpoint.");
            int out = (s.nextLine().contains("ff")) ? tm.fastForward() : tm.step();
            System.out.println(tm);
            while (out == 2) {
                out = (s.nextLine().contains("ff")) ? tm.fastForward() : tm.step();
                System.out.println(tm);

            }
            if (out == 1) {
                System.out.println("Your Turing Machine REJECTS input " + input);
            } else if (out == 0) {
                System.out.println("Your Turing Machine ACCEPTS input " + input);
            }
            System.out.println("Try another input? (Y/N)");
            addInput = getYesNo(s);
        }

        System.out.println("Here is your TM code for future testing!");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(tm.getCode());
        s.close();

    }

    public static boolean getYesNo(Scanner s) {
        String x = s.nextLine();
        if (x.trim().equals("Y") || x.trim().equals("y")) {
            return true;
        } else if (x.trim().equals("N") || x.trim().equals("n")) {
            return false;
        } else {
            return getYesNo(s);
        }
    }
}