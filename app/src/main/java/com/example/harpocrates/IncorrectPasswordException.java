package com.example.harpocrates;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException(){
        super("the password you have provided is incorrect");
    }
    public IncorrectPasswordException(String message){
        super(message);
    }

}
