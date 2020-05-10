package model;

import java.util.Enumeration;
public class EnumString implements Enumeration<String> {
    private String string;

    public EnumString(String string){
        this.string = string;
    }

    public boolean hasMoreElements() {
        return string.length() != 0;
    }

    public String nextElement() {
        return hasMoreElements()? string.substring(string.length()-1) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnumString that = (EnumString) o;
        return string.equals(that.string);
    }

    @Override
    public int hashCode() {
        return string.hashCode();
    }
}
