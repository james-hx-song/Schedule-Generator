package com.example.schedulegenerator.Model;

import java.util.ArrayList;

public class User {

    private String uid;
    private String name;
    private String email;
    private ArrayList<String> projectList;
    private String role;

    public User()
    {

    }
    public User(String uid, String name, String email, ArrayList<String> projectList, String role) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.projectList = projectList;
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getProjectList() {
        return projectList;
    }

    public void setProjectList(ArrayList<String> projectList) {
        this.projectList = projectList;
    }
}
