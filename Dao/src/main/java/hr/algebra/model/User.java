/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import hr.algebra.enumm.UserEnum;

/**
 *
 * @author lorenababic
 */
public class User {
    
    private int id;
    private String username;
    private String password;
    private UserEnum userRole;

    public User(int id, String username, String password, UserEnum userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public User(String username, String password, UserEnum userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public User(int id, UserEnum userRole) {
        this.id = id;
        this.userRole = userRole;
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

    public UserEnum getUserRole() {
        return userRole;
    }

    public void setUserRole(UserEnum userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", userRole=" + userRole + '}';
    }

}
