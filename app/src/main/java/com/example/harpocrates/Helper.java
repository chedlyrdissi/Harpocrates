package com.example.harpocrates;

import java.util.ArrayList;
import java.util.List;

import piece.Piece;

public class    Helper {
    //TODO
    public static boolean validUserName(String username) {
        return username!=null;
    }
    //TODO
    public static boolean validPassword(String password) {
        return password!=null;
    }
    //TODO
    public static boolean usableContext(String context){
        return true;
    }
    //TODO
    private static boolean listOfInfoIsValid(String[] s){return true;}

    public static List<Piece> createDummyList(int items, int values) {
        ArrayList<Piece> list=new ArrayList<>();
        Piece piece;
        for (int j=0; j<items; j++) {
            piece=new Piece("title"+j);
            for (int o=0; o<values ; o++) {
                piece.add("key"+j,"value"+j);
            }
        }
        return list;
    }
}
