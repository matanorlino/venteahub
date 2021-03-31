package com.dehaja.venteahubmilktea.models;

import java.io.Serializable;

public class VenteaUser implements Serializable {
    private int id;
    private String username;
    private String password;
    private String email;
    private String contact_no;
    private String accesslevel;

    public VenteaUser () {}

    public VenteaUser(int id, String username, String password, String email, String contact_no, String accesslevel) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getAccesslevel() {
        return accesslevel;
    }

    public void setAccesslevel(String accesslevel) {
        this.accesslevel = accesslevel;
    }
}
