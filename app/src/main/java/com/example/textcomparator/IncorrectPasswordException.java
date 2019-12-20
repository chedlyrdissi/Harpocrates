package com.example.textcomparator;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException(){
        super("the password you have provided is incorrect");
    }
}
