package com.herokuapp.meetnlunch.meetnlunch;


/**
 * Created by Shirco on 24/02/2016.
 */
public class User {
    private String name;
    private String authentication_token;
    private String logged_at;
    private String email;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return authentication_token;
    }

    public void setToken(String token) {
        this.authentication_token = token;
    }

    public String getLogged_at() {
        return logged_at;
    }

    public void setLogged_at(String logged_at) {
        this.logged_at = logged_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
