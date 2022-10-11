package com.example.test2;

public class PersonaInformation {
    private String fullName;
    private String howToReadName;
    private String sex;
    private String birthDay;
    private String country;
    private String postCode;
    private String address;
    private String phoneNumber;

    public PersonaInformation(String fullName, String howToReadName, String sex, String birthDay, String country, String postCode, String address, String phoneNumber) {
        this.fullName = fullName;
        this.howToReadName = howToReadName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.country = country;
        this.postCode = postCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public PersonaInformation() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHowToReadName() {
        return howToReadName;
    }

    public void setHowToReadName(String howToReadName) {
        this.howToReadName = howToReadName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
