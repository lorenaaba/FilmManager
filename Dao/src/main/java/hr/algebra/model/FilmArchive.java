/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lorenababic
 */
@XmlRootElement(name = "filmarchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class FilmArchive {
    
    @XmlElementWrapper
    @XmlElement(name = "film")
    private List<Film> films;

    public FilmArchive() {
    }

    public FilmArchive(List<Film> films) {
        this.films = films;
    }

    public List<Film> getFilms() {
        return films;
    }
    
}
