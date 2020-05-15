package model;

public class IterableString {
    public String string;

    public IterableString(String string){
        this.string = string;
    }

    public boolean hasMore() {
        return string.length() != 0;
    }

    public IterableString cutNext() {
        return hasMore()? new IterableString(string.substring(0,string.length()-1)) : null;
    }
    public char getNext(){
        return string.charAt(string.length()-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IterableString that = (IterableString) o;
        return string.equals(that.string);
    }

    @Override
    public int hashCode() {
        return string.hashCode();
    }
}
