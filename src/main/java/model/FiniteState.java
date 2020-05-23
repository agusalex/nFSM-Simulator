package model;
import java.util.HashMap;
import java.util.Map;


public class FiniteState
{
    private Map<Character, FiniteState> map;
    private String name;
    public FiniteState(Map<Character, FiniteState> map, String name ){
        this.map = map;
        this.name = name;
    }
    public FiniteState(String name ){
        this.name = name;
        this.map = new HashMap<>();
    }
    public FiniteState transition(Character q ){
        return map.get(q);
    }

    public Map<Character, FiniteState> getTransitions(){
        return map;
    }

    public void addTransition(Character c, FiniteState s){
        map.put(c,s);
    }

}
