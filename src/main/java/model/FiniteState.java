package model;

import java.util.Enumeration;
import java.util.HashMap;


public abstract class FiniteState<E extends Enumeration> implements State<E>
{
    private HashMap<E, FiniteState<E>> map;

    public FiniteState(HashMap<E, FiniteState<E>> map){
        this.map = map;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiniteState<?> that = (FiniteState<?>) o;
        return map.equals(that.map);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
