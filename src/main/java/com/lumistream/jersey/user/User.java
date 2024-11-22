package com.lumistream.jersey.user;

public class User {

    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }


    public void addUser(User u){
        //ir à base de dados adicionar entrada
    }

    public User getUser(){
        return this;
    }

    public void login(String name, String pass){
        //criar sistema de login
    }

    public void deleteUser(){
        //ir à base de dados remover entrada
    }
}
