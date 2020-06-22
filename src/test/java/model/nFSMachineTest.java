package model;

import converter.FSMachineFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class nFSMachineTest {

    nFSMachine plainNumbers;
    nFSMachine jsonNumbers;

    @Before
    public void setUp() {
        plainNumbers = FSMachineFactory.get().FromPlainText_ND("helloWorld_numbers_nd");
        jsonNumbers = FSMachineFactory.get().FromJson_ND("helloWorld_numbers_nd");

    }
    @Test
    public void parse(){
       Assert.assertEquals(plainNumbers.toString(),jsonNumbers.toString());
    }
    @Test
    public void run() throws Exception {
        Assert.assertTrue(plainNumbers.run("101"));
        Assert.assertFalse(plainNumbers.run("000"));
    }

    @Test
    public void debug(){
        Assert.assertEquals("[[( 1, 2)], [( 0, 2)], [( 1, 2)]]",plainNumbers.debug("101").toString());
        Assert.assertEquals("[[( 0, 1)], [( 0, 1)], [( 0, 1)]]",plainNumbers.debug("000").toString());
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
    public void testPass2() throws Exception {
        nFSMachine  onlya = FSMachineFactory.get().FromPlainText_ND("stringsFinishedWithA");
   //     Assert.assertFalse( onlya.run("bbbb"));
        Assert.assertTrue( onlya.run("bbbba"));
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

    @Test
    public void convertNFAtoDFA(){
        FSMachineFactory.get().nfa2DfaPlainText("");
    }





}
