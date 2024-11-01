/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.enumm.Role;
import hr.algebra.enumm.UserEnum;
import hr.algebra.model.Film;
import hr.algebra.model.Person;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;

/**
 *
 * @author lorenababic
 */
public class SqlRepository implements Repository{
    
    private static final String ID_PERSON = "IDPerson";
    private static final String NAME = "Name";
    private static final String ROLE = "Role";
    
    private static final String ID_USER = "IdUser";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String USER_ROLE = "Role";
        
    
    private static final String CREATE_PERSON = "{ CALL createPerson (?,?,?) }";
    private static final String UPDATE_PERSON = "{ CALL updatePerson (?,?,?) }";
    private static final String DELETE_PERSON = "{ CALL deletePerson (?) }";
    private static final String SELECT_PERSON = "{ CALL selectPerson (?) }";
    private static final String SELECT_PEOPLE = "{ CALL selectPeople }";
    private static final String SELECT_ACTORS = "{ CALL selectActors }";
    private static final String SELECT_DIRECTORS = "{ CALL selectDirectors }";
    private static final String FIND_PERSON = "{ CALL findPerson (?,?) }";
    
    private static final String CREATE_USER = "{ CALL createUser (?,?,?,?) }";
    private static final String SELECT_USER = "{ CALL selectUser (?,?) }";
        
    private static final String MAX_PERSON_ID = "{ CALL maxPersonId }";

    private static final String ID_FILM = "IDFIlm";
    private static final String TITLE = "Title";
    private static final String DURATION = "Duration";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "Description";
    private static final String PICTURE_PATH = "PicturePath";
    
    private static final String PERSON_ID = "PersonId";
    private static final String FILM_ID = "FilmId";    
    private static final String ROLE_FILM = "RoleInFilm";
    private static final String ID_FILM_PERSON = "IdFilmPerson";
    
    
    private static final String CREATE_FILM = "{ CALL createFilm (?,?,?,?,?,?) }";
    private static final String UPDATE_FILM = "{ CALL updateFilm (?,?,?,?,?,?) }";
    private static final String DELETE_FILM = "{ CALL deleteFilm (?) }";
    private static final String SELECT_FILM = "{ CALL selectFilm (?) }";
    private static final String SELECT_FILMS = "{ CALL selectFilms }";
    private static final String FIND_FILM = "{ CALL findFilm (?) }";
    
    private static final String SELECT_PERSON_IN_FILM = "{ CALL selectPersonInFilm (?) }";
    private static final String CREATE_PERSON_IN_FILM = "{ CALL createPersonInFilm (?,?,?,?) }"; 
    private static final String DELETE_PERSON_IN_FILM = "{ CALL deletePersonInFilm (?) }";

    private static final String MAX_FILM_ID = "{ CALL maxFilmId }";    

    @Override
    public int createPerson(Person person) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_PERSON)) {
            stmt.setString(NAME, person.getName());
            stmt.setString(ROLE, person.getRole().name());
            
            stmt.registerOutParameter(ID_PERSON, Types.INTEGER);
            stmt.executeUpdate();
            
            return stmt.getInt(ID_PERSON);
        } 
    }

    @Override
    public void createPeople(List<Person> people) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_PERSON)) {
            for(Person person : people) {
                stmt.setString(NAME, person.getName());
                stmt.setString(ROLE, person.getRole().name());
                stmt.registerOutParameter(ID_PERSON, Types.INTEGER);
                stmt.executeUpdate();
            }
        }
    }

    /**
     *
     * @param id
     * @param data
     * @throws Exception
     */
    @Override
    public void updatePerson(int id, Person data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_PERSON)) {
                stmt.setString(NAME, data.getName());
                stmt.setString(ROLE, data.getRole().name());
                
                stmt.setInt(ID_PERSON, id);
                stmt.executeUpdate();
        }
    }

    @Override
    public void deletePerson(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_PERSON)) { 
            stmt.setInt(ID_PERSON, id);
            stmt.executeUpdate();
        } 
    }

    @Override
    public Optional<Person> selectPerson(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_PERSON)) { 
            stmt.setInt(ID_PERSON, id);
            
            try(ResultSet rs = stmt.executeQuery();) {
                if(rs.next()) {
                    return Optional.of(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(NAME),
                            Role.fromString(rs.getString(ROLE))
                    ));
                }
            }
        }
        return Optional.empty();
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Person> selectPeople() throws Exception {
        List<Person> people = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_PEOPLE); ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                people.add(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(NAME),
                            Role.fromString(rs.getString(ROLE))                        
                ));
            }
        }         
        return people;
    }

    @Override
    public List<Person> selectActors() throws Exception {
        List<Person> actors = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ACTORS); ResultSet rs = stmt.executeQuery()) {  
            while(rs.next()) {
                actors.add(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(NAME),
                            Role.fromString(rs.getString(ROLE))                         
                ));
            }
        }      
        return actors;
    }

    @Override
    public List<Person> selectDirectors() throws Exception {
        List<Person> directors = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS); ResultSet rs = stmt.executeQuery()) {  
            while(rs.next()) {
                directors.add(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(NAME),
                            Role.fromString(rs.getString(ROLE))                         
                ));
            }
        }      
        return directors;
    }

    @Override
    public int createFilm(Film film) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_FILM);) {
            stmt.setString(TITLE, film.getTitle());
            stmt.setInt(DURATION, film.getDuration());
            stmt.setString(PUBLISHED_DATE, film.getPublishedDate().format(Film.DATE_FORMATTER));
            stmt.setString(DESCRIPTION, film.getDescription());
            stmt.setString(PICTURE_PATH, film.getPicturePath());
            stmt.registerOutParameter(ID_FILM, Types.INTEGER);
            
            stmt.executeUpdate();
            return stmt.getInt(ID_FILM);
            
        }
    }

    @Override
    public void createFilms(List<Film> films) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_FILM);) {
            for(Film film : films) {
                stmt.setString(TITLE, film.getTitle());
                stmt.setInt(DURATION, film.getDuration());
                stmt.setString(PUBLISHED_DATE, film.getPublishedDate().format(Film.DATE_FORMATTER));
                stmt.setString(DESCRIPTION, film.getDescription());
                stmt.setString(PICTURE_PATH, film.getPicturePath());
                stmt.registerOutParameter(ID_FILM, Types.INTEGER);
            
                stmt.executeUpdate();            
            }
            
        }
    }

    @Override
    public void updateFilm(int id, Film data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_FILM);) {
                stmt.setString(TITLE, data.getTitle());
                stmt.setInt(DURATION, data.getDuration());
                stmt.setString(PUBLISHED_DATE, data.getPublishedDate().format(Film.DATE_FORMATTER));
                stmt.setString(DESCRIPTION, data.getDescription());
                stmt.setString(PICTURE_PATH, data.getPicturePath());
                stmt.setInt(ID_FILM, id);
                
                stmt.executeUpdate();
        }
    }

    @Override
    public void deleteFilm(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_FILM);) {
            stmt.setInt(ID_FILM, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Film> selectFilm(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_FILM);) {
            stmt.setInt(ID_FILM, id);
            
            try (ResultSet rs = stmt.executeQuery();) {
                if(rs.next()) {
                    return Optional.of(new Film (
                            rs.getInt(ID_FILM),
                            rs.getString(TITLE),
                            rs.getInt(DURATION),
                            LocalDate.parse(rs.getString(PUBLISHED_DATE), Film.DATE_FORMATTER),
                            rs.getString(DESCRIPTION),
                            rs.getString(PICTURE_PATH)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Film> selectFilms() throws Exception {
        List<Film> films = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_FILMS); ResultSet rs = stmt.executeQuery()) { 
            while(rs.next()) {
                films.add(new Film(
                        rs.getInt(ID_FILM),
                        rs.getString(TITLE),
                        rs.getInt(DURATION),
                        LocalDate.parse(rs.getString(PUBLISHED_DATE), Film.DATE_FORMATTER),
                        rs.getString(DESCRIPTION),
                        rs.getString(PICTURE_PATH)                    
                ));
            }
        }       
        return films;
    }

    @Override
    public int createCast(int filmId, int personId, Person person) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_PERSON_IN_FILM);) {

            stmt.setInt(PERSON_ID, personId);
            stmt.setInt(FILM_ID, filmId);
            stmt.setString(ROLE_FILM, person.getRole().name());

            stmt.registerOutParameter(ID_FILM_PERSON, Types.INTEGER);
            stmt.executeUpdate();

            int createdFilmId = stmt.getInt(ID_FILM);
            
            createCastAll(createdFilmId, Film.getCast());

            return createdFilmId;
        }
    }

    @Override
    public void createCastAll(int createdFilmId, Set<Person> people) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_PERSON_IN_FILM);) {

            for (Person person : people) {
                stmt.setInt(PERSON_ID, person.getId());
                stmt.setInt(FILM_ID, createdFilmId);
                stmt.setString(ROLE_FILM, person.getRole().name());

                stmt.registerOutParameter(ID_FILM_PERSON, Types.INTEGER);
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public Set<Person> selectCast(int filmId) throws Exception {
        Set<Person> castSet = new HashSet<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_PERSON_IN_FILM);) {

            stmt.setInt(FILM_ID, filmId);

            try (ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    castSet.add(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(NAME),
                            Role.fromString(rs.getString(ROLE))
                    ));
                }
            }
        }
        return castSet;
    }

    @Override
    public void deleteCast(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_PERSON_IN_FILM);) {

            stmt.setInt(FILM_ID, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public int createUser(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER);) {
            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASSWORD, user.getPassword());
            stmt.setString(USER_ROLE, user.getUserRole().name());

            stmt.registerOutParameter(ID_USER, Types.INTEGER);
            stmt.executeUpdate();

            return stmt.getInt(ID_USER);
        }
    }

    @Override
    public Optional<User> selectUser(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_USER);) {

            stmt.setString(USERNAME, username);
            stmt.setString(PASSWORD, password);

            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt(ID_USER),
                            UserEnum.fromString(rs.getString(USER_ROLE))
                    ));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> findPerson(Person person) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(FIND_PERSON);) {
                stmt.setString(NAME, person.getName());
                stmt.setString(ROLE, person.getRole().name());  
                
            try(ResultSet rs = stmt.executeQuery();) {
                if(rs.next()){
                    return Optional.ofNullable(rs.getInt(ID_PERSON));
                }
            }                   
        }  
        return Optional.empty();
    }

    @Override
    public Optional<Integer> findFilm(String film) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(FIND_FILM);) {
            stmt.setString(TITLE, film);
            
            try(ResultSet rs = stmt.executeQuery();) {
                if(rs.next()){
                    return Optional.ofNullable(rs.getInt(ID_FILM));
                }
            }
        }  
        return Optional.empty(); 
    }

    @Override
    public Optional<Integer> maxFilmId() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(MAX_FILM_ID);) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.execute();
            
            int result = stmt.getInt(1);
            
            if(!stmt.wasNull()) {
                return Optional.of(result);
            }
        }
        return Optional.empty(); 
    }

    @Override
    public Optional<Integer> maxPersonId() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(MAX_PERSON_ID);) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.execute();
            
            int result = stmt.getInt(1);
            
            if(!stmt.wasNull()) {
                return Optional.of(result);
            }
        }
        return Optional.empty(); 
    }

    @Override
    public void clearAllData() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall("{ CALL clearAllData() }")) {
            stmt.executeUpdate();
        }
    }
    
}
