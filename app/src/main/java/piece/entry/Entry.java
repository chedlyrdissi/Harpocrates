package piece.entry;

public class Entry {

    private long id;
    private String key;
    private boolean keyVisible;
    private String value;
    private boolean valueVisible;

    public Entry( long id, String key, String value ){
        this.id = id;
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
        if ( other==null || !(other instanceof Entry) ) {
            return false;
        }
        Entry o = (Entry) other;
        return this.id == o.id;
    }

    public String toString(){
        return key+" = "+value;
    }

    public static boolean isValidKey( String key ){
        if ( key == null || key.isEmpty() ) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidValue( String value ) {
        if ( value == null || value.isEmpty() ) {
            return false;
        } else {
            return true;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isValueVisible() {
        return valueVisible;
    }

    public void setValueVisible(boolean valueVisible) {
        this.valueVisible = valueVisible;
    }

    public boolean isKeyVisible() {
        return keyVisible;
    }

    public void setKeyVisible(boolean keyVisible) {
        this.keyVisible = keyVisible;
    }
}
