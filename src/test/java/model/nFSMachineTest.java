package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class nFSMachineTest {
    private nFSMachine getNFsMachineTestCase() {
        HashMap<Character, Set<nState>> finalStateTransitions = new HashMap<>();
        nState finalState = new nState(finalStateTransitions, "final");
        HashSet<nState> transitionsFor1Final = new HashSet<>();
        transitionsFor1Final.add(finalState);
        finalStateTransitions.put('1', transitionsFor1Final);
        finalStateTransitions.put('0', transitionsFor1Final);
        HashMap<Character, Set<nState>> initialStateTransitions = new HashMap<>();
        initialStateTransitions.put('1', transitionsFor1Final);
        initialStateTransitions.put('0', transitionsFor1Final);
        nState initialState = new nState(initialStateTransitions, "initial");

        HashSet<nState> finalStates = new HashSet<>();

        finalStates.add(finalState);
        return new nFSMachine(initialState, finalStates);
    }
    FSMachine plainNumbers;
    FSMachine jsonNumbers;


    @Test
    public void manual(){
        System.out.println(getNFsMachineTestCase().debug("111010101"));
    }

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






}
