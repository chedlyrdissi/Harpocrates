package com.example.textcomparator;

public class TextComparatorDataBase {

    private static TextComparatorDataBase instance;

    private TextComparatorDataBase(){
    }

    public static TextComparatorDataBase getInstance(){
        if (instance==null) {
            instance=new TextComparatorDataBase();
        }
        return instance;
    }

}
