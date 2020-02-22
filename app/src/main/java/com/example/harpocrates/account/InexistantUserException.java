package com.example.harpocrates.account;

public class InexistantUserException extends Exception {
    public InexistantUserException( int id ) {
        super( "the user_id " + id + " does not exist in our database" );
    }
}
