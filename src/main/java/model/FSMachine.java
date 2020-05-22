package model;

import java.util.Set;

public class FSMachine {
    private FiniteState initialState;
    private Set<FiniteState> acceptingStates;

    public FSMachine(FiniteState initialState, Set<FiniteState> acceptingStates){
        this.initialState = initialState;
        this.acceptingStates = acceptingStates;
    }


    public boolean run(String input) {
        return acceptingStates.contains(transition(initialState, input));
    }

    private FiniteState transition(FiniteState q, String a){
        if(a.length() == 0) return q;

        char c = a.charAt(a.length()-1);
        String smaller = a.substring(0,a.length()-1);
        System.err.println(smaller);


        return transition(q.transition(c),smaller);
    }

}
