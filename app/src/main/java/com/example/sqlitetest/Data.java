package com.example.sqlitetest;

public class Data {

    private int id;
    private String companyName;
    private String password;
    private String email;

    public Data(String companyName, String email,String password) {
        this.companyName = companyName;
        this.email = email;
        this.password = password;
    }

    public Data(int id, String companyName, String email, String password) {
        this.id = id;
        this.companyName = companyName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
