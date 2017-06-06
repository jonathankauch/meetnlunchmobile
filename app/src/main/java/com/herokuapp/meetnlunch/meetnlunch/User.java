package com.herokuapp.meetnlunch.meetnlunch;


/**
 * Created by Shirco on 24/02/2016.
 */
public class User {
    private String name;
    private String email;
    private int id;
    private int food;
    private int wantedAge;
    private String wantedGender;
    private int visibilityRange;
    private int age;
    private int visibleAge;
    private String gender;
    private String visibleGender;
    private String description;
    private String position;
    private boolean showGender;
    private boolean showAge;
    private boolean isVisible;
    private String contact;

    public int getFoodId() {
        return food;
    }

    public void setFoodId(int foodId) {
        this.food = foodId;
    }

    public int getWantedAge() {
        return wantedAge;
    }

    public void setWantedAge(int wantedAge) {
        this.wantedAge = wantedAge;
    }

    public String getWantedGender() {
        return wantedGender;
    }

    public void setWantedGender(String wantedGender) {
        this.wantedGender = wantedGender;
    }

    public int getVisibleAge() {
        return visibleAge;
    }

    public void setVisibleAge(int visibleAge) {
        this.visibleAge = visibleAge;
    }

    public String getVisibleGender() {
        return visibleGender;
    }

    public void setVisibleGender(String visibleGender) {
        this.visibleGender = visibleGender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRange() {
        return visibilityRange;
    }

    public void setRange(int range) {
        this.visibilityRange = range;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isShowGender() {
        return showGender;
    }

    public void setShowGender(boolean showGender) {
        this.showGender = showGender;
    }

    public boolean isShowAge() {
        return showAge;
    }

    public void setShowAge(boolean showAge) {
        this.showAge = showAge;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getWantedGenderId() {
        if (wantedGender.contains("M")) {
            return 0;
        } else if (wantedGender.contains("F")) {
            return 1;
        }
        return 2;
    }

    public int getVisibleGenderId() {
        if (visibleGender.contains("M")) {
            return 0;
        } else if (visibleGender.contains("F")) {
            return 1;
        }
        return 2;
    }

}
