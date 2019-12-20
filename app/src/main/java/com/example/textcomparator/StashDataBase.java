package com.example.textcomparator;

public class StashDataBase {

    private static StashDataBase instance;
    private static DummyDataBase dummy;

    private StashDataBase(){
        dummy=new DummyDataBase();
    }

    public static StashDataBase getInstance(){
        if (instance==null) {
            instance=new StashDataBase();
        }
        return instance;
    }

    public void authenticate(String username,String password) throws
            IncorrectPasswordException,IncorrectUsernameException{
        //TODO
        if(dummy.get(username)==null) throw new IncorrectUsernameException();
        if(!dummy.get(username).equals(password)) throw new IncorrectPasswordException();
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

    private boolean usernameExists(String username) {
        return true;
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
