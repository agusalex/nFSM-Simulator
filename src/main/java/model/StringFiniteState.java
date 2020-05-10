package model;

import java.util.HashMap;

public class StringFiniteState extends FiniteState<EnumString> {

    public StringFiniteState(HashMap<EnumString, FiniteState<EnumString>> map) {
        super(map);
    }

    public void transition(EnumString input) {

    }
}
