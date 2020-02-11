package com.example.harpocrates.user;

import android.content.Intent;

public class UserBean {

    private static UserBean instance;

    private User currentUser;
    private User hiddenUser;

    public static UserBean getInstance() {

        if ( instance == null )
            instance=new UserBean();

        return instance;
    }

    private UserBean() {
    }


    public User getCurrentUser() {
        return currentUser;
    }

    //TODO implement
    public Intent authenticate() {

        return null;
    }

    public User getHiddenUser() {
        return hiddenUser;
    }

    public void setHiddenUser(User hiddenUser) {
        this.hiddenUser = hiddenUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean hasHiddenUser () {
        return hiddenUser != null;
    }
}
