package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class FSMachine {
    private final State initialState;
    private final Set<State> acceptingStates;
    private String name = "";



    public FSMachine(String name, State initialState, Set<State> acceptingStates) {
        this.name = name;
        this.initialState = initialState;
        this.acceptingStates = acceptingStates;
    }


    public boolean run(String input) throws Exception {
        return acceptingStates.contains(transition(initialState, input));
    }

    public ArrayList<Tuple<String, String>> debug(String input) {
        return debugTransition(initialState, input, new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final String[] accepting = {"{ "};
        acceptingStates.forEach(a-> accepting[0] +=a.getName());
        accepting[0]+=" }";

        return "FSMachine(" +this.name+"){"+
                "initialState=" + initialState.getName() +
                ", acceptingStates=" + accepting +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FSMachine fsMachine = (FSMachine) o;
        return Objects.equals(initialState, fsMachine.initialState) &&
                Objects.equals(acceptingStates, fsMachine.acceptingStates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialState, acceptingStates);
    }

    private State transition(State q, String a) throws Exception {
        if (a.length() == 0) return q;
        char c = a.charAt(0);
        String smaller = a.substring(1);
        return transition(q.transition(c), smaller);
    }

    private ArrayList<Tuple<String, String>> debugTransition(State q, String a, ArrayList<Tuple<String, String>> history) {

        if (a.length() == 0) return history;
        char c = a.charAt(0);
        String smaller = a.substring(1);
        System.out.println(smaller);
        history.add(new Tuple<>("" + c, q.getName()));
        try {
            debugTransition(q.transition(c), smaller, history);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return history;
    }

}
