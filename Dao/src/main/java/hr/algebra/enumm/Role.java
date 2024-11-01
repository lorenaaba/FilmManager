/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.enumm;

/**
 *
 * @author lorenababic
 */
public enum Role {
    ACTOR,
    DIRECTOR;
    
    public static Role fromString(String value) {
        if(value != null) {
            switch (value.toUpperCase()) {
                case "ACTOR" -> {
                    return ACTOR;
                }
                case "DIRECTOR" -> {
                    return DIRECTOR;
                }
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }
    
}
