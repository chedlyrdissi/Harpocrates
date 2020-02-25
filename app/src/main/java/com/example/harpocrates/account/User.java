package com.example.harpocrates.account;

public class User {

    private long id;
    private String username;
    private String password; // hashed
    private String creationDate;
    private String lastModified;

    public User( long id, String username, String password ) {
        this(id,username,password,"","");
    }

    public User( long id, String username ) { // TODO remove pw section
        this(id,username,"","","");
    }

    public User( long id,
                 String username,
                 String password,
                 String creationDate,
                 String lastModified ) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
    }

    public long getID() {
        return id;
    }

    public void setID( long ID ) {
        this.id = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String toString() {
        return    "id : "           + id            + "\n"
                + "username : "     + username      + "\n"
                + "password : "     + password      + "\n"
                + "creationDate : " + creationDate  + "\n"
                + "lastModified : " + lastModified  + "\n";
    }
}
