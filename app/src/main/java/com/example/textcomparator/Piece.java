package com.example.textcomparator;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

public class Piece {

    private String context;
    private Map<String,String> infoMap;

    public Piece(String context){
        this.context=context;
        infoMap=new HashMap<>();
    }

    public String getContext(){
        return context;
    }

    public void setContext(String context) {
        if (validContext(context))
            this.context = context;
    }

    public Map<String, String> getInfoMap() {
        return infoMap;
    }

    public void setInfoMap(Map<String, String> infoMap) {
        if (validMap(infoMap))
            this.infoMap = infoMap;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean add(String key, String value){

        if (!validKey(key) || !validValue(value))
            return false;

        return infoMap.putIfAbsent(key,value) == null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean replace(String key, String value){

        if (!validKey(key) || !validValue(value))
            return false;

        infoMap.replace(key,value);

        return true;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (obj==null) return false;
        if (obj.getClass()!=getClass()) return false;

        Piece other=(Piece) obj;

        return this.context.equals(other.context)
                && this.infoMap.equals(other.infoMap);

    }

    public String getInfoString(){
        StringBuffer buffer=new StringBuffer();
        for (String key:infoMap.keySet()){
            buffer.append(key+" = "+infoMap.get(key)+"\n");
        }
        return buffer.toString();
    }

    @NonNull
    @Override
    public String toString() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(context);
        buffer.append(getInfoString());
        return buffer.toString();
    }

    //******************************************//
    //      validation methods                  //
    //******************************************//

    private static boolean validContext(String context){
        if (context==null) return false;

        return true;
    }

    private static boolean validMap(Map<String, String> infoMap) {
        if (infoMap==null) return false;

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
