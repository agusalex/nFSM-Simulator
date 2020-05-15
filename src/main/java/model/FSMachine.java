package model;

import java.util.Set;

public class FSMachine {
    private FiniteState initialState;
    private Set<FiniteState> acceptingStates;

    public FSMachine(FiniteState initialState, Set<FiniteState> acceptingStates){
        this.initialState = initialState;
        this.acceptingStates = acceptingStates;
    }


    public boolean run(IterableString input) {
        return acceptingStates.contains(transition(initialState, input));
    }

    private FiniteState transition(FiniteState q, IterableString a){
        if(!a.hasMore()) return q;

        IterableString string= a.cutNext();
        System.err.println(string.string);

        char c = a.getNext();

        return transition(q.transition(c),string);
    }


}
