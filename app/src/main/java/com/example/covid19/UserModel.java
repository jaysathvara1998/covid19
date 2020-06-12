package com.example.covid19;

public class UserModel {
    public UserModel() {
    }

    public UserModel(String firstName, String lastName, String email, String password, String coronaStatus, String address, String city, String country, String isHide) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coronaStatus = coronaStatus;
        this.address = address;
        this.city = city;
        this.country = country;
        this.isHide = isHide;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCoronaStatus() {
        return coronaStatus;
    }

    public void setCoronaStatus(String coronaStatus) {
        this.coronaStatus = coronaStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsHide() {
        return isHide;
    }

    public void setIsHide(String isHide) {
        this.isHide = isHide;
    }

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String coronaStatus;
    private String address;
    private String city;
    private String country;
    private String isHide;
}
