/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.enumm;

/**
 *
 * @author lorenababic
 */
public enum UserEnum {
    USER,
    ADMIN;
    
    public static UserEnum fromString(String value) {
        if(value != null) {
            switch(value.toUpperCase()) {
                case "USER" -> {
                    return USER;
                }
                case "ADMIN" -> {
                    return ADMIN;
                }
            }
        }
        throw new IllegalArgumentException("Invalid user : " + value);
    }
    
}
