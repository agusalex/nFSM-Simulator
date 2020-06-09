package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class FSMachine {
    private State initialState;
    private Set<State> acceptingStates;

    public FSMachine(State initialState, Set<State> acceptingStates) {
        this.initialState = initialState;
        this.acceptingStates = acceptingStates;
    }


    public boolean run(String input) {
        return acceptingStates.contains(transition(initialState, input));
    }

    public ArrayList<Tuple<String,String>> debug(String input) {
        return debugTransition(initialState, input, new ArrayList<>());
    }

    @Override
    public String toString() {
        return "FSMachine{" +
                "initialState=" + initialState +
                ", acceptingStates=" + acceptingStates +
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

    private State transition(State q, String a) {
        if (a.length() == 0) return q;
        char c = a.charAt(a.length() - 1);
        String smaller = a.substring(0, a.length() - 1);
        return transition(q.transition(c), smaller);
    }

    private ArrayList<Tuple<String,String>> debugTransition(State q, String a, ArrayList<Tuple<String,String>> history) {

        if (a.length() == 0) return history;
        char c = a.charAt(a.length() - 1);
        String smaller = a.substring(0, a.length() - 1);
        System.out.println(smaller);
        history.add(new Tuple<>(""+c,q.getName()));
        debugTransition(q.transition(c), smaller,history);
        return history;
    }

}
