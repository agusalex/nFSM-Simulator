package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class nState {
    private final Map<Character, Set<nState>> map;
    private final String name;

    public nState(Map<Character, Set<nState>> map, String name) {
        this.map = map;
        this.name = name;
    }

    public nState(String name) {
        this.name = name;
        this.map = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        nState that = (nState) o;
        return Objects.equals(map, that.map) &&
                Objects.equals(name, that.name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {

        String transitions = "{";
        for (Map.Entry<Character, Set<nState>> entry : map.entrySet()) {
            transitions += "Char = " + entry.getKey() +
                    ", State = " + entry.getValue() + ", ";

        }
        transitions += "}";


        return "FiniteState{" +
                "map=" + transitions +
                " , name='" + name + '\'' +
                '}';
    }


    public Set<nState> transition(Character q) {
        return map.get(q);
    }

    public Map<Character, Set<nState>> getTransitions() {
        return map;
    }

    public void addTransition(Character c, Set<nState> s) {
        map.put(c, s);
    }

}
