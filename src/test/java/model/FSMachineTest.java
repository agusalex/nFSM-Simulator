package model;


import com.sun.tools.javac.util.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

public class FSMachineTest {


    @Test
    void run() {
        HashMap<Character,FiniteState> finalStateTransitions = new HashMap<>();
        FiniteState finalState = new FiniteState(finalStateTransitions);
        finalStateTransitions.put('1',finalState);
        finalStateTransitions.put('0',finalState);
        HashMap<Character,FiniteState> initialStateTransitions = new HashMap<>();
        initialStateTransitions.put('1',finalState);
        initialStateTransitions.put('0',finalState);
        FiniteState initialState = new FiniteState(initialStateTransitions);

        HashSet<FiniteState> finalStates = new HashSet<>();

        finalStates.add(finalState);
        FSMachine machine = new FSMachine(initialState,finalStates);
        Assert.check(machine.run(new IterableString("1010101101")));
    }
}
