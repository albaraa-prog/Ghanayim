package com.example.app1;

public class UserModel{
    private String emailtxt,Nametxt,usertxt,PhoneNumber,city;

    //make empty constfructer


    public UserModel(String nametxt, String usertxt, String emailtxt, String phoneNumber, String city) {
        this.emailtxt = emailtxt;
        this.Nametxt = nametxt;
        this.usertxt = usertxt;
        this.PhoneNumber = phoneNumber;
        this.city = city;
    }
    //make getter and setter


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNametxt() {
        return Nametxt;
    }

    public void setNametxt(String nametxt) {
        Nametxt = nametxt;
    }

    public String getUsertxt() {
        return usertxt;
    }

    public void setUsertxt(String usertxt) {
        this.usertxt = usertxt;
    }

    public String getEmailtxt() {
        return emailtxt;
    }

    public void setEmailtxt(String emailtxt) {
        this.emailtxt = emailtxt;
    }


    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
