package com.example.harpocrates.account;

public class InexistantUserException extends Exception {
    public InexistantUserException( long id ) {
        super( "the user_id " + id + " does not exist in our database" );
    }
}
