package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class State {
    private final Map<Character, State> map;
    private final String name;

    public State(Map<Character, State> map, String name) {
        this.map = map;
        this.name = name;
    }

    public State(String name) {
        this.name = name;
        this.map = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State that = (State) o;
        return Objects.equals(map, that.map) &&
                Objects.equals(name, that.name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {

        String transitions = "{";
        for (Map.Entry<Character, State> entry : map.entrySet()) {
            transitions += "Char = " + entry.getKey() +
                    ", State = " + entry.getValue().name + ", ";

        }
        transitions += "}";


        return "FiniteState{" +
                "map=" + transitions +
                " , name='" + name + '\'' +
                '}';
    }


    public State transition(Character q) throws Exception {
        if(map.get(q)==null){
            throw new Exception("there doesnt exist a transition for character: "+q+" in state: "+name);
        }
        return map.get(q);
    }

    public Map<Character, State> getTransitions() {
        return map;
    }

    public void addTransition(Character c, State s) {
        map.put(c, s);
    }

}
