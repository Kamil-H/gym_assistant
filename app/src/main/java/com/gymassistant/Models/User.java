package com.gymassistant.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-09.
 */
public class User {
    @SerializedName("Id")
    private int id;
    @SerializedName("AddedDate")
    private String addedDate;
    @SerializedName("Birthday")
    private String birthday;
    @SerializedName("Email")
    private String email;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("Gender")
    private String gender;
    @SerializedName("Password")
    private String password;
    @SerializedName("Surname")
    private String surname;
    @SerializedName("UserName")
    private String userName;
    @SerializedName("Users")
    private List<User> userList = new ArrayList<User>();

    public User(String email, String firstName, String gender, String password, String surname, String userName) {
        this.email = email;
        this.firstName = firstName;
        this.gender = gender;
        this.password = password;
        this.surname = surname;
        this.userName = userName;
    }

    public User(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", addedDate='" + addedDate + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", surname='" + surname + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
