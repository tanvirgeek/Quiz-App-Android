package com.tanvirgeek.quizeexamapp;

public class User {


    private int id;
    private String fullName;
    private String userName;
    private String collegeName;
    private String dob;
    private String email;
    private String mobileNo;
    private String gender;
    private String password;

    public User() {
    }

    public User(String fullName, String userName, String collegeName, String dob, String email, String gender, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.collegeName = collegeName;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }



}
