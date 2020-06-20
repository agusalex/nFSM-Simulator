package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FSMachineTest {
    private FSMachine getFsMachineTestCase() {
        HashMap<Character, State> finalStateTransitions = new HashMap<>();
        State finalState = new State(finalStateTransitions, "final");
        finalStateTransitions.put('1', finalState);
        finalStateTransitions.put('0', finalState);
        HashMap<Character, State> initialStateTransitions = new HashMap<>();
        initialStateTransitions.put('1', finalState);
        initialStateTransitions.put('0', finalState);
        State initialState = new State(initialStateTransitions, "initial");

        HashSet<State> finalStates = new HashSet<>();

        finalStates.add(finalState);
        return new FSMachine(initialState, finalStates);
    }
    FSMachine plainNumbers;
    FSMachine jsonNumbers;

    @Before
    public void setUp() {
        plainNumbers = FSMachineFactory.get().FromPlainText("helloWorld_numbers");
        jsonNumbers = FSMachineFactory.get().FromJson("helloWorld_numbers");
    }
    @Test
    public void parse(){
        Assert.assertEquals(plainNumbers.toString(),jsonNumbers.toString());
    }
    @Test
    public void run(){
        Assert.assertEquals("[( 1, 1), ( 0, 2), ( 1, 2)]",jsonNumbers.debug("101").toString());
    }

    @Test
    public void testFail() throws Exception {
        Assert.assertFalse(jsonNumbers.run("000"));
        Assert.assertFalse(plainNumbers.run("000"));


    }
    @Test
    public void testPass() throws Exception {
        Assert.assertTrue( jsonNumbers.run("001"));
        Assert.assertTrue( plainNumbers.run("001"));
    }
    @Test
    public void testOutOfLanguage(){
        try {
            jsonNumbers.run("001e");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"there doesnt exist a transition for character: e in state: 1");
        }

    }


}
