package com0.coastalcasa.Models;


public class Landlord{

    private long id;

    private String email;

    private String password;

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassword(){
        return password;
    }
    public void setPassord(String pass){
        this.password = pass;
    }

}