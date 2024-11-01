/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Film;
import hr.algebra.model.Person;
import hr.algebra.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author lorenababic
 */
public interface Repository {
    
    int createPerson(Person person) throws Exception;
    void createPeople(List<Person> people) throws Exception;
    void updatePerson(int id, Person person) throws Exception;
    void deletePerson(int id) throws Exception;
    
    Optional<Person> selectPerson(int id) throws Exception;
    List<Person> selectPeople() throws Exception;
    List<Person> selectActors() throws Exception;
    List<Person> selectDirectors() throws Exception;
    
    int createFilm(Film film) throws Exception;
    void createFilms(List<Film> films) throws Exception;
    void updateFilm(int id, Film film) throws Exception;
    void deleteFilm(int id) throws Exception;
    Optional<Film> selectFilm(int id) throws Exception;
    List<Film> selectFilms() throws Exception;    
    
    int createCast(int filmId, int personId, Person person) throws Exception;
    void createCastAll(int createdFilmId, Set<Person> person) throws Exception;
    Set<Person> selectCast(int filmId) throws Exception;
    public void deleteCast(int id) throws Exception;
    
    int createUser(User user) throws Exception;
    Optional<User> selectUser(String username, String password) throws Exception;

    Optional<Integer> findPerson(Person person) throws Exception;
    Optional<Integer> findFilm(String film) throws Exception;

    
    Optional<Integer> maxFilmId() throws Exception;
    Optional<Integer> maxPersonId() throws Exception;
    
    
    void clearAllData() throws Exception;

    
}
