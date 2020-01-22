package piece;

import java.util.ArrayList;
import java.util.List;

public class Piece {

    private String title;
    private List<Entry> items;

    public Piece(String title){
        this.title=title;
        items=new ArrayList<>();
    }

    public String gettitle(){
        return title;
    }

    public void settitle(String title) {
        if (validtitle(title))
            this.title = title;
    }

    public List<Entry> getItems() {
        return items;
    }

    public void setItems(List<Entry> items) {
        if (validItems(items))
            this.items = items;
    }

    public boolean add(String key, String value){

        if (!validKey(key) || !validValue(value))
            return false;

        Entry entry=new Entry(key,value);

        for (int i=0;i<items.size();i++) {
            if (items.get(i).equals(entry)) {
                return false;
            }
        }

        return items.add(entry);
    }

    public boolean replaceOrAdd(String key, String value, String newValue){

        if (!validKey(key) || !validValue(value))
            return false;

        Entry entry=new Entry(key,value);

        int i=0;
        while ( i<items.size() ) {
            if (items.get(i).equals(entry)) {
                break;
            }
            i++;
        }

        if ( i==items.size() ) {
            items.add(entry);
        }else{
            items.get(i).setValue(newValue);
        }

        return true;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj==null) return false;
        if (obj.getClass()!=getClass()) return false;

        Piece other=(Piece) obj;

        return this.title.equals(other.title)
                && this.items.equals(other.items);

    }

    public String getItemInfo( int i ) {
        return items.get(i).getValue();
    }

    public int itemCount() {return items.size();}

    //******************************************//
    //      validation methods                  //
    //******************************************//

    private static boolean validtitle(String title){
        if (title==null) return false;

        return true;
    }

    private static boolean validItems(List<Entry> items) {
        if (items==null) return false;

        return true;
    }

    private static boolean validKey(String key){
        if (key==null) return false;
        return true;
    }

    private static boolean validValue(String value){
        if (value==null) return false;
        return true;
    }

}
