package com.example.textcomparator;

public class StashDataBase {

    private static StashDataBase instance;

    private StashDataBase(){
    }

    public static StashDataBase getInstance(){
        if (instance==null) {
            instance=new StashDataBase();
        }
        return instance;
    }

    public boolean usernameExists(String username){
        //TODO
        return true;
    }

    public boolean checkPassword(String username,String password){
        //TODO
        return true;
    }

}
