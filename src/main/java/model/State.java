package model;

import java.util.Enumeration;

public interface State<E extends Enumeration> {

        void transition( E input);

}
