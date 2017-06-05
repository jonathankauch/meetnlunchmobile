package com.herokuapp.meetnlunch.meetnlunch;


/**
 * Created by Shirco on 24/02/2016.
 */
public class User {
    private String name;
    private String authentication_token;
    private String logged_at;
    private String email;
    private int customer_id;
    private int food_id;
    private int wanted_age;
    private String wanted_gender;
    private int range;
    private int visible_age;
    private String visible_gender;
    private String description;
    private boolean search;
    private boolean show_gender;
    private boolean show_age;
    private String contact;


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

    public String getAuthentication_token() {
        return authentication_token;
    }

    public void setAuthentication_token(String authentication_token) {
        this.authentication_token = authentication_token;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getWanted_age() {
        return wanted_age;
    }

    public void setWanted_age(int wanted_age) {
        this.wanted_age = wanted_age;
    }

    public String getWanted_gender() {
        return wanted_gender;
    }

    public void setWanted_gender(String wanted_gender) {
        this.wanted_gender = wanted_gender;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getVisible_age() {
        return visible_age;
    }

    public void setVisible_age(int visible_age) {
        this.visible_age = visible_age;
    }

    public String getVisible_gender() {
        return visible_gender;
    }

    public void setVisible_gender(String visible_gender) {
        this.visible_gender = visible_gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public boolean isShow_gender() {
        return show_gender;
    }

    public void setShow_gender(boolean show_gender) {
        this.show_gender = show_gender;
    }

    public boolean isShow_age() {
        return show_age;
    }

    public void setShow_age(boolean show_age) {
        this.show_age = show_age;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
