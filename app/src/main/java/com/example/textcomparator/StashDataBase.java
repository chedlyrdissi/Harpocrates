package com.example.textcomparator;

import android.content.Context;

import java.util.List;

public class StashDataBase {

    private static StashDataBase instance;
    //private static DummyDataBase dummy;
    private static InternalHandler internaldb;

    private StashDataBase(Context context){
      //  dummy=new DummyDataBase();
        internaldb=new InternalHandler(context);
    }

    public static StashDataBase getInstance(Context context){
        if (instance==null) {
            instance=new StashDataBase(context);
        }
        return instance;
    }

    public void authenticate(String username,String password) throws
            IncorrectPasswordException,IncorrectUsernameException{
        //TODO
        //if(dummy.get(username)==null) throw new IncorrectUsernameException();
        //if(!dummy.get(username).equals(password)) throw new IncorrectPasswordException();
        if(!internaldb.accountExists(username)) throw new IncorrectUsernameException();
        String pw=internaldb.getPassword(username);
        if(pw==null || password==null) throw new IncorrectPasswordException();
        pw=decode(pw);
        if(pw==null) throw new IncorrectPasswordException();
        if(!pw.equals(password)) throw new IncorrectPasswordException();
    }

    public AccountType getAccountType(String username,String password){

        if (username==null || password==null) throw new IllegalArgumentException("the arguments must not be null");

        if (isGod(username,password))
            return AccountType.admin;

        if(usernameExists(username))
            if(decode(password).equals(getPassword(username)))
                return AccountType.regular;

        return AccountType.irregular;
    }

    public void createAccount(String username,String password){
        internaldb.addAccount(username,password);
    }

    public boolean usernameExists(String username) {
        return internaldb.accountExists(username);
    }

    protected boolean isGod(String username,String password){
        if (username==null || password==null) return false;
        if (username.equals("") && password.equals("admin")) return true;
        return false;
    }

    public List<String> getAccountList(){
        return internaldb.getAccounts();
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
