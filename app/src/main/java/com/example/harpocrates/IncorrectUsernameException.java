package com.example.harpocrates;

public class IncorrectUsernameException extends Exception {
    public IncorrectUsernameException(){
        super("the username you have provided is incorrect");
    }
    public IncorrectUsernameException(String message){
        super(message);
    }
}
