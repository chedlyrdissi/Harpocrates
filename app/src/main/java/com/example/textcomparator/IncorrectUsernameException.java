package com.example.textcomparator;

public class IncorrectUsernameException extends Exception {
    public IncorrectUsernameException(){
        super("the username you have provided is incorrect");
    }
}
