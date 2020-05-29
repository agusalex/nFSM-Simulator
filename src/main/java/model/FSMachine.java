package model;

import java.util.Set;

public class FSMachine {
    private State initialState;
    private Set<State> acceptingStates;

    public FSMachine(State initialState, Set<State> acceptingStates){
        this.initialState = initialState;
        this.acceptingStates = acceptingStates;
    }


    public boolean run(String input) {
        return acceptingStates.contains(transition(initialState, input));
    }

    private State transition(State q, String a){
        if(a.length() == 0) return q;

        char c = a.charAt(a.length()-1);
        String smaller = a.substring(0,a.length()-1);
        System.out.println(smaller);


        return transition(q.transition(c),smaller);
    }

}
