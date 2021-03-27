package com.dehaja.venteahubmilktea.models;

public class VenteaUser {
    private int id;
    private String username;
    private String password;
    private String contact_no;
    private String accesslevel;

    public VenteaUser () {}

    public VenteaUser(int id, String username, String password, String contact_no, String accesslevel) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.contact_no = contact_no;
        this.accesslevel = accesslevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact_no() { return this.contact_no; }

    public String getAccesslevel() {
        return accesslevel;
    }

    public void setAccesslevel(String accesslevel) {
        this.accesslevel = accesslevel;
    }
}
