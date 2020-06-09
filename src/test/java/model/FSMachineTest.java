package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    @Test
    public void run() {
        FSMachine machine = getFsMachineTestCase();
    //    Assert.assertEquals(getFsMachineFromJson("helloWorld"),getFSMachineFromPlainText("helloWorld"));
        FSMachine plainNumbers = getFSMachineFromPlainText("helloWorld_numbers");
        FSMachine jsonNumbers = getFsMachineFromJson("helloWorld_numbers");
        Assert.assertEquals(plainNumbers.toString(),jsonNumbers.toString());
        Assert.assertEquals(plainNumbers.debug("101010"),jsonNumbers.debug("101010"));
    }


    public FSMachine getFSMachineFromPlainText(String fsmFile){

        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/"+fsmFile+".fsm"), StandardCharsets.UTF_8));
        HashMap<String, HashSet<Tuple<Character, String>>> transitions = new HashMap<>();
        ArrayList<String> Final = new ArrayList<>();
        ArrayList<String> initial = new ArrayList<>();
        initial.add("1");
        try {
            String[] language = br.readLine().replaceAll("\\s+","").split(",");
            int stateCount = Integer.parseInt(br.readLine());
            Final = new ArrayList<>(Arrays.asList(br.readLine().replaceAll("\\s+","").split(",")));

            for (int i = 0; i < stateCount*language.length; i++) {
                String line = br.readLine().replaceAll("\\s+","");

                String[] fields = line.split(",");
                String from = fields[0];
                String[] transition = fields[1].split("->");
                transitions.computeIfAbsent(from, k -> new HashSet<>());
                transitions.get(from).add(new Tuple<>(transition[0].charAt(0),transition[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return buildFSMachine(transitions, initial, Final);

    }

    private FSMachine getFsMachineFromJson(String jsonFile) {
        JSONObject jsonObject = readResource(new JSONParser(), "/"+jsonFile+".json");
        HashMap<String, JSONObject> jsonStates = buildStates(jsonObject);
        HashSet<Character> language = buildLanguage(jsonObject);

        final ArrayList<String> initial = new ArrayList<>();
        final ArrayList<String> Final = new ArrayList<>();
        HashMap<String, HashSet<Tuple<Character, String>>> transitions = buildTransitions(jsonStates, initial, Final, language);

        return buildFSMachine(transitions, initial, Final);
    }

    private HashMap<String, JSONObject> buildStates(JSONObject jsonObject) {
        JSONArray statesJson = (JSONArray) jsonObject.get("states");
        HashMap<String, JSONObject> jsonStates = new HashMap<>();

        statesJson.forEach(state -> {
            String name = (String) ((JSONObject) state).get("name");
            jsonStates.put(name, (JSONObject) state);
        });
        return jsonStates;
    }

    private HashSet<Character> buildLanguage(JSONObject jsonObject) {
        HashSet<Character> language = new HashSet<>();
        ((JSONArray) jsonObject.get("language")).
                forEach(character -> language.add(character.toString().charAt(0)));

        return language;
    }

    private HashMap<String, HashSet<Tuple<Character, String>>> buildTransitions(HashMap<String, JSONObject> jsonStates, ArrayList<String> initial, ArrayList<String> Final, HashSet<Character> language) {
        HashMap<String, HashSet<Tuple<Character, String>>> transitions = new HashMap<>();
        for (Map.Entry<String, JSONObject> entry : jsonStates.entrySet()) {
            String name = entry.getKey();
            JSONObject state = entry.getValue();
            for (Character character : language) {
                JSONArray transitionF =
                        (JSONArray) state.get(character.toString());


                transitionF.forEach(jsonTransition -> {

                            HashSet<Tuple<Character, String>> transitionSet = new HashSet<>();
                            transitionF.forEach(to ->
                                    transitionSet.add(new Tuple<>(character, to.toString())));

                            if (transitions.containsKey(name)) {
                                transitionSet.forEach(triple -> transitions.get(name).add(triple));
                            } else {
                                transitions.put(name, transitionSet);
                            }

                        }
                );

            }
            if (state.get("initial") != null) initial.add(name);
            if (state.get("final") != null) Final.add(name);
        }
        return transitions;
    }

    private FSMachine buildFSMachine(HashMap<String, HashSet<Tuple<Character, String>>> transitions, ArrayList<String> initial, ArrayList<String> Final) {


        HashMap<String, State> states = new HashMap<>();

        transitions.forEach((k, v) -> states.put(k, new State(k)));

        transitions.forEach((k, v) -> {
            State state = states.get(k);
            v.forEach(transition -> state.addTransition(transition.getFirst(), states.get(transition.getSecond())));
        });


        State initialState = states.get(initial.get(0));
        HashSet<State> finalStates = new HashSet<>();
        Final.forEach(state -> finalStates.add(states.get(state)));
        return new FSMachine(initialState, finalStates);
    }


    public JSONObject readResource(JSONParser parser, String filename) {
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
