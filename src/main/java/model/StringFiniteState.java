package model;

import java.util.HashMap;

public class StringFiniteState extends FiniteState<EnumString> {

    public StringFiniteState(HashMap<EnumString, FiniteState<EnumString>> map) {
        super(map);
    }

    public void transition(EnumString input) {
        if(input.hasMoreElements()){
            EnumString element = input.nextElement();
            if(this.map.containsKey(element)){
                this.map.get(element).transition(element);
            }
        }
    }
}
