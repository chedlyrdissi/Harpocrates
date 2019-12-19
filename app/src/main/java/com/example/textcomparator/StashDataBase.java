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

    public AccountType getAccountType(String username,String password){

        if (username==null || password==null) throw new IllegalArgumentException("the arguments must not be null");

        if (username.isEmpty() && decode(password).equals(getPassword(username)))
            return AccountType.admin;

        if(usernameExists(username))
            if(decode(password).equals(getPassword(username)))
                return AccountType.regular;

        return AccountType.irregular;
    }

    //TODO
    private String encode(String word){
        return word;
    }

    //TODO
    private String decode(String word){
        return word;
    }

    //TODO
    private String getPassword(String username){
        return new DummyDataBase().get(username);
    }
}
