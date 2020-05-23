package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FSMachineTest {


    @Test
    public void run() {
        HashMap<Character,FiniteState> finalStateTransitions = new HashMap<>();
        FiniteState finalState = new FiniteState(finalStateTransitions,"final");
        finalStateTransitions.put('1',finalState);
        finalStateTransitions.put('0',finalState);
        HashMap<Character,FiniteState> initialStateTransitions = new HashMap<>();
        initialStateTransitions.put('1',finalState);
        initialStateTransitions.put('0',finalState);
        FiniteState initialState = new FiniteState(initialStateTransitions,"initial");

        HashSet<FiniteState> finalStates = new HashSet<>();

        finalStates.add(finalState);
        FSMachine machine = new FSMachine(initialState,finalStates);
        machine.run("1010101101");
    }

    @Test
    public void run2()  {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = readResource(parser,"/helloWorld.json");
        Boolean type = (Boolean) jsonObject.get("deterministic");
        HashMap<String, JSONObject> jsonStates = new HashMap<>();
        HashMap<String, HashSet<Tuple<Character,String>>> transitions = new HashMap<>();
        final ArrayList<String> initial = new ArrayList<>();
        final ArrayList<String> Final = new ArrayList<>();
        HashSet<Character> language= new HashSet<>();
        JSONArray statesJson = (JSONArray) jsonObject.get("states");


        ((JSONArray) jsonObject.get("language")).
                forEach(character -> language.add(character.toString().charAt(0)));
       statesJson.forEach(state -> {
                    String name = (String) ((JSONObject) state).get("name");
                    jsonStates.put(name,(JSONObject) state);
                });

        jsonStates.forEach((name,state)-> {language.forEach(character -> {


            JSONArray transitionF =
                    (JSONArray) state.get(character.toString());


            transitionF.forEach(jsonTransition ->{

                        HashSet<Tuple<Character,String>> transitionSet = new HashSet<>();
                        transitionF.forEach(to ->
                                transitionSet.add(new Tuple<>(character, to.toString())));

                        if(transitions.containsKey(name)){
                            transitionSet.forEach(triple -> transitions.get(name).add(triple));
                        }
                        else{
                            transitions.put(name,transitionSet);}

                    }
                     );

        });
            if (state.get("initial")!=null) initial.add(name);
            if (state.get("final")!=null) Final.add(name);
        });

        System.err.println("Language: "+language +",Initial: "+initial.get(0)+" ,Final: "+Final+" Transitions: "+transitions);
        printGraph(buildGraph(initial.get(0),transitions));
    }


    public void printGraph(FiniteState initial){

       initial.getTransitions().forEach((c,v) ->{
           System.err.println(c+" -> "+v.toString());
           printGraph(v);
       });
    }


    public FiniteState buildGraph(String node, HashMap<String, HashSet<Tuple<Character,String>>> transitions){




        HashMap<Character, FiniteState> map = new HashMap<>();
        for (Tuple<Character,String> tuple:transitions.get(node)
             ) {

            if(!node.equals(tuple.getSecond())) { //not "caso base"
                FiniteState newState = buildGraph(tuple.getSecond(), transitions);
                map.put(tuple.getFirst(),newState);
            }

        }


        return new FiniteState( map,node);
    }


    public JSONObject readResource(JSONParser parser,String filename){
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename), StandardCharsets.UTF_8));
        Object jsonObj = null;
        try {
            jsonObj = parser.parse(br);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return (JSONObject) jsonObj;
    }

}
