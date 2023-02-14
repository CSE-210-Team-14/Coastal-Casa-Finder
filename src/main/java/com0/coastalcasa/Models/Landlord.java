package com0.coastalcasa.Models;

import javax.persistence.*;

@Entity // for specifies class is an entity and is mapped to a database table. 
public class Landlord{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @Column
    private String email;
    @Column
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