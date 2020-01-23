package com.example.harpocrates;

import android.content.Intent;

import com.example.harpocrates.user.User;

class UserBean {

    private static UserBean instance;

    private User currentUser;

    static UserBean getInstance() {

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

}
