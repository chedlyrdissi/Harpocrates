package com.example.harpocrates;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public static boolean usableTitle(String title){
        return title != null && !title.isEmpty();
    }
    //TODO
    private static boolean listOfInfoIsValid(String[] s){return true;}

    public static List<Piece> createDummyList(int items, int values) {
        ArrayList<Piece> list=new ArrayList<>();
        Piece piece;
        for (int j=0; j<items; j++) {
            piece=new Piece(j,"title"+j);
            for (int o=0; o<values ; o++) {
                piece.add("key"+o,"value"+o);
            }
            list.add(piece);
        }
        return list;
    }

    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
