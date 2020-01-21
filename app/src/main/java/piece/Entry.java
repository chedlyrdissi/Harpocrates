package piece;

public class Entry {
    private String key;
    private String value;

    public Entry(String key, String value){
        this.key=key;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(Object other){

        if (other==null) return false;
        if ( !(other instanceof Entry) ) return false;

        return this.key.equals(((Entry) other).key)
                && this.value.equals(((Entry) other).value);
    }

    public String toString(){
        return key+" = "+value;
    }
}
