package model;

import java.util.*;

public class nFSMachine {
    private final nState initialState;
    private final Set<nState> acceptingStates;

    public nFSMachine(nState initialState, Set<nState> acceptingStates) {
        this.initialState = initialState;
        this.acceptingStates = acceptingStates;
    }


    public boolean run(String input) {
        acceptingStates.retainAll(transition(initialState, input));
        return acceptingStates.size()>=1;
    }

    public ArrayList<HashSet<Tuple<String, String>>> debug(String input){
        ArrayList<HashSet<Tuple<String, String>>> history = new ArrayList<>();
        debugTransition(initialState,input,history);
        return history;
    }


    @Override
    public String toString() {
        final String[] accepting = {"{ "};
        acceptingStates.forEach(a-> accepting[0] +=", "+a.getName());
        accepting[0]+=" }";

        return "FSMachine{" +
                "initialState=" + initialState.getName() +
                ", acceptingStates=" + Arrays.toString(accepting) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        nFSMachine fsMachine = (nFSMachine) o;
        return Objects.equals(initialState, fsMachine.initialState) &&
                Objects.equals(acceptingStates, fsMachine.acceptingStates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialState, acceptingStates);
    }

    private HashSet<nState> transition(nState q, String a) {
        HashSet<nState> ret = new HashSet<>();
        if (a.length() == 0){
            ret.add(q);
            return ret;
        }
        char c = a.charAt(a.length() - 1);
        String smaller = a.substring(0, a.length() - 1);
        for (nState state:
        q.transition(c)) {
            ret.addAll(transition(state, smaller));
        }

        return ret;

    }


private HashSet<nState> debugTransition(nState q, String a, ArrayList<HashSet<Tuple<String, String>>> history ) {
        HashSet<nState> ret = new HashSet<>();
        if (a.length() == 0){
            ret.add(q);
            return ret;
        }
        char c = a.charAt(a.length() - 1);
        String smaller = a.substring(0, a.length() - 1);
        HashSet<Tuple<String,String>> historyItem = new HashSet<>();
        for (nState state:
                q.transition(c)) {
            ret.addAll(debugTransition(state, smaller,history));
            historyItem.add(new Tuple<>(""+c,q.getName()));
        }
        history.add(historyItem);

        return ret;
    }


}
