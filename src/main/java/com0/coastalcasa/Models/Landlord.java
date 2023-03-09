package com0.coastalcasa.Models;


public class Landlord{
    private String email;
    private String password;

    public Landlord(String string, String string2) {
        this.email = string;
        this.password = string2;
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
    public void setPassword(String pass){
        this.password = pass;
    }

}