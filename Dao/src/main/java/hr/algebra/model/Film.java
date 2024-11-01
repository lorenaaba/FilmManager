/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author lorenababic
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "title", "duration", "publishedDate", "description", "actors", "directors", "picturePath"})
public class Film {
    
    private static Set<Person> cast;

    public static Set<Person> getCast() {
        return cast != null ? cast : new HashSet<>();  
    }
    
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    
    private int id;
    
    @XmlElement(name = "title")
    private String title;
    
    @XmlElement(name = "duration")
    private int duration;
    
    @XmlElement(name = "publishedDate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDate publishedDate;
    
    @XmlElement(name = "description")
    private String description;
    
    @XmlElementWrapper
    @XmlElement(name = "actor")
    private Set<Person> actors;
    
    @XmlElementWrapper
    @XmlElement(name = "director")
    private Set<Person> directors;  
    
    @XmlElement(name = "picturePath")
    private String picturePath;

    public Film() {
    }

    public Film(int id, String title, int duration, LocalDate publishedDate, String description, Set<Person> actors, Set<Person> directors, String picturePath) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.publishedDate = publishedDate;
        this.description = description;
        this.actors = actors;
        this.directors = directors;
        this.picturePath = picturePath;
    }
    
    

    public Film(int id, String title, int duration, LocalDate publishedDate, String description, String picturePath) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.publishedDate = publishedDate;
        this.description = description;
        this.picturePath = picturePath;
    }

    public Film(String title, int duration, LocalDate publishedDate, String description, String picturePath) {
        this.title = title;
        this.duration = duration;
        this.publishedDate = publishedDate;
        this.description = description;
        this.picturePath = picturePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.title);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Film other = (Film) obj;
        return true;
    }
    
    

    @Override
    public String toString() {
        return "Film{" + "id=" + id + ", title=" + title + ", duration=" + duration + ", publishedDate=" + publishedDate + ", description=" + description + ", picturePath=" + picturePath + '}';
    }
  
    
}
