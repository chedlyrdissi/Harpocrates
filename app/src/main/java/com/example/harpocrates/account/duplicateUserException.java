package com.example.harpocrates.account;

class duplicateUserException extends Exception {
    public duplicateUserException(long id, String username) {
        super("the account with \n"
                + "id = " + id + "\n"
                + "username = " + username + "\n"
                + "has a duplicate");
    }
}
