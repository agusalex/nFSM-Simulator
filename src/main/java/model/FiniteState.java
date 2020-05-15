package model;
import java.util.Map;


public class FiniteState
{
    protected Map<Character, FiniteState> map;

    public FiniteState(Map<Character, FiniteState> map){
        this.map = map;
    }
    public FiniteState transition(Character q ){
        return map.get(q);
    }

}
