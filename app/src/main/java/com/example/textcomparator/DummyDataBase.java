package com.example.textcomparator;

import java.util.HashMap;
import java.util.Map;

public class DummyDataBase {
    private Map<String,String> map;

    public DummyDataBase(){
        map=new HashMap<>();
        map.put("","admin");
        map.put("Regular","regular");
    }

    public String get(String username){
        return map.get(username);
    }
}
