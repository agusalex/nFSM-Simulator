package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class nFSMachineTest {
  /*  private nFSMachine getNFsMachineTestCase() {
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



    @Test
    public void manual(){
        System.out.println(getNFsMachineTestCase().debug("111010101"));
    }*/
    nFSMachine plainNumbers;
   // nFSMachine jsonNumbers;
    @Before
    public void setUp() {
        plainNumbers = FSMachineFactory.get().FromPlainText_ND("helloWorld_numbers");
       // jsonNumbers = FSMachineFactory.get().FromJson_ND("helloWorld_numbers_nd");
    }
    @Test
    public void parse(){
     //   System.out.println(jsonNumbers.toString());
    //    Assert.assertEquals(plainNumbers.toString(),jsonNumbers.toString());
    }
    @Test
    public void debug(){
        System.out.println(plainNumbers);
        Assert.assertTrue(plainNumbers.run("101"));
        Assert.assertFalse(plainNumbers.run("000"));
       // Assert.assertEquals("[( 1, 1), ( 0, 2), ( 1, 2)]",plainNumbers.debug("101").toString());

    }






}
