package com.herokuapp.meetnlunch.meetnlunch;

/**
 * Created by Shirco on 26/01/2016.
 */
public class Singleton {
    private static Singleton mInstance = null;

    private String mToken;
    private String mId;
    private User mUser;
    private boolean init = false;

    private Singleton(String token, String id){
        mToken = token;
        mId = id;
    }

    public static Singleton getInstance(String token, String id){
        if(mInstance == null)
        {
            mInstance = new Singleton(token, id);
        } else {
            mInstance.setToken(token);
            mInstance.setId(id);
        }
        return mInstance;
    }

    public static Singleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new Singleton("", "");
        }
        return mInstance;
    }

    public String getToken(){
        return this.mToken;
    }

    public void setToken(String value){
        mToken = value;
    }

    public String getId(){
        return this.mId;
    }

    public void setId(String value){
        mId = value;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

}