package converter;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FSMachineFactory {
    static FSMachineFactory instance;

    private FSMachineFactory() {

    }

    public static FSMachineFactory get() {
        if (instance == null) {
            instance = new FSMachineFactory();
        }
        return instance;
    }

    public nFSMachine FromPlainText_ND(String nfsmFile) {
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + nfsmFile + ".nfsm"), StandardCharsets.UTF_8));
        HashMap<String, HashSet<Tuple<Character, String>>> transitions = new HashMap<>();
        ArrayList<String> Final = new ArrayList<>();
        ArrayList<String> initial = new ArrayList<>();
        initial.add("1");
        HashSet<Character> language = new HashSet<>();
        try {
            String[] lang = br.readLine().replaceAll("\\s+", "").split(",");
            Arrays.asList(lang).forEach(c -> language.add(c.charAt(0)));
            int stateCount = Integer.parseInt(br.readLine()); //Dont comment this
            Final = new ArrayList<>(Arrays.asList(br.readLine().replaceAll("\\s+", "").split(",")));
            String crudeLine = br.readLine();
            while (crudeLine != null) {
                String line = crudeLine.replaceAll("\\s+", "");
                String[] fields = line.split(",");
                String from = fields[0];
                String[] transition = fields[1].split("->");
                transitions.computeIfAbsent(from, k -> new HashSet<>());
                transitions.get(from).add(new Tuple<>(transition[0].charAt(0), transition[1]));
                crudeLine = br.readLine();
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
        System.out.println("transitions: "+transitions +" ,initial: "+initial+" Final: "+Final+" language: "+language);
        return buildNFSMachine(transitions, initial, Final, language, nfsmFile);
    }

    public String nfa2DfaPlainText(String nfsmFile) {
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + nfsmFile + ".nfsm"), StandardCharsets.UTF_8));
        HashMap<String, HashSet<Tuple<Character, String>>> transitions = new HashMap<>();
        ArrayList<String> Final = new ArrayList<>();
        ArrayList<String> initial = new ArrayList<>();
        initial.add("1");
        HashSet<Character> language = new HashSet<>();
        try {
            String[] lang = br.readLine().replaceAll("\\s+", "").split(",");
            Arrays.asList(lang).forEach(c -> language.add(c.charAt(0)));
            int stateCount = Integer.parseInt(br.readLine()); //Dont comment this
            Final = new ArrayList<>(Arrays.asList(br.readLine().replaceAll("\\s+", "").split(",")));
            String crudeLine = br.readLine();
            while (crudeLine != null) {
                String line = crudeLine.replaceAll("\\s+", "");
                String[] fields = line.split(",");
                String from = fields[0];
                String[] transition = fields[1].split("->");
                transitions.computeIfAbsent(from, k -> new HashSet<>());
                transitions.get(from).add(new Tuple<>(transition[0].charAt(0), transition[1]));
                crudeLine = br.readLine();
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
        System.out.println(initial.get(0) +" "+ transitions +" "+ Final +" "+ language);
        return new NFSConverter(initial.get(0),transitions, Final, language).convert();
    }


    public FSMachine FromPlainText(String fsmFile) {

        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + fsmFile + ".fsm"), StandardCharsets.UTF_8));
        HashMap<String, HashSet<Tuple<Character, String>>> transitions = new HashMap<>();
        ArrayList<String> Final = new ArrayList<>();
        ArrayList<String> initial = new ArrayList<>();
        initial.add("1");
        try {
            String[] language = br.readLine().replaceAll("\\s+", "").split(",");
            int stateCount = Integer.parseInt(br.readLine());
            Final = new ArrayList<>(Arrays.asList(br.readLine().replaceAll("\\s+", "").split(",")));

            for (int i = 0; i < stateCount * language.length; i++) {
                String line = br.readLine().replaceAll("\\s+", "");

                String[] fields = line.split(",");
                String from = fields[0];
                String[] transition = fields[1].split("->");
                transitions.computeIfAbsent(from, k -> new HashSet<>());
                transitions.get(from).add(new Tuple<>(transition[0].charAt(0), transition[1]));
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

        return buildFSMachine(transitions, initial, Final, "Transitions( "+fsmFile+" )"+transitions);

    }

    public nFSMachine FromJson_ND(String jsonFile) {
        JSONObject jsonObject = readResource(new JSONParser(), "/" + jsonFile + ".json");
        try{
            if(((Boolean) (jsonObject.get("deterministic")))){
                throw new Exception("This machine is not signed as Non deterministic");
            }
        }
        catch (Exception e) {
            new Exception("This machine is not signed as Non deterministic").printStackTrace();
        }


        HashMap<String, JSONObject> jsonStates = buildStates(jsonObject);


        HashSet<Character> language = buildLanguage(jsonObject);

        final ArrayList<String> initial = new ArrayList<>();
        final ArrayList<String> Final = new ArrayList<>();
        HashMap<String, HashSet<Tuple<Character, String>>> transitions = buildTransitions(jsonStates, initial, Final, language);
        return buildNFSMachine(transitions, initial, Final, language, jsonFile);
    }




    public FSMachine FromJson(String jsonFile) {
        JSONObject jsonObject = readResource(new JSONParser(), "/" + jsonFile + ".json");
        HashMap<String, JSONObject> jsonStates = buildStates(jsonObject);
        HashSet<Character> language = buildLanguage(jsonObject);

        final ArrayList<String> initial = new ArrayList<>();
        final ArrayList<String> Final = new ArrayList<>();
        HashMap<String, HashSet<Tuple<Character, String>>> transitions = buildTransitions(jsonStates, initial, Final, language);
        return buildFSMachine(transitions, initial, Final, jsonFile);
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

    private nFSMachine buildNFSMachine(HashMap<String, HashSet<Tuple<Character, String>>> transitions, ArrayList<String> initial, ArrayList<String> Final, HashSet<Character> language, String name) {

        HashMap<String, nState> states = new HashMap<>();  //KEY= name of state, Value = Object Reference of State
        transitions.forEach((k, v) -> states.put(k, new nState(k)));

        transitions.forEach((k, v) -> {
            nState state = states.get(k); //Get Object Reference

            language.forEach(c -> state.addTransitions(c, filter(v, c, states))); //Por cada valor del set quedarse solo con los que tienen el char que queres
        });

        nState initialState = states.get(initial.get(0));
        HashSet<nState> finalStates = new HashSet<>();
        Final.forEach(state -> finalStates.add(states.get(state)));
        return new nFSMachine(name+"( "+transitions+" ): ", initialState, finalStates);
    }

    private HashSet<nState> filter(HashSet<Tuple<Character, String>> origin, Character c, HashMap<String, nState> states) {
        HashSet<nState> filtered = new HashSet<>();
        for (Tuple<Character, String> tuple : origin
        ) {
            if (tuple.getFirst().equals(c)) {

                filtered.add(states.get(tuple.getSecond()));
            }

        }
        return filtered;
    }

    private FSMachine buildFSMachine(HashMap<String, HashSet<Tuple<Character, String>>> transitions, ArrayList<String> initial, ArrayList<String> Final, String name) {


        HashMap<String, State> states = new HashMap<>();

        transitions.forEach((k, v) -> states.put(k, new State(k)));

        transitions.forEach((k, v) -> {
            State state = states.get(k);
            v.forEach(transition -> state.addTransition(transition.getFirst(), states.get(transition.getSecond())));
        });


        State initialState = states.get(initial.get(0));
        HashSet<State> finalStates = new HashSet<>();
        Final.forEach(state -> finalStates.add(states.get(state)));
        return new FSMachine("Transitions( "+name+" ):"+transitions, initialState, finalStates);
    }

    private JSONObject readResource(JSONParser parser, String filename) {
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
