package com.example.coursework2.model;
import com.google.gson.annotations.SerializedName;

public class User {
    // убрала expose
    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
