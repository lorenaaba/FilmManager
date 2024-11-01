/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.parsers.rss;


import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Film;
import hr.algebra.utilities.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author lorenababic
 */
public class FilmParser {
    
   private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    
    private static final Set<Film> filmSet = new HashSet<>();
   
    
    private static final String RSS_URL = "https://www.blitz-cinestar-bh.ba/rss.aspx?id=2682";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    

    
    
    public static List<Film> parse() throws IOException, XMLStreamException, Exception {
        List<Film> films = new ArrayList<>();
                
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        
        try(InputStream is = con.getInputStream())
        {
            XMLEventReader reader = ParserFactory.createStaxParser(is);
            
            Optional<TagType> tagType = Optional.empty();
            Film film = null;
            StartElement startElement = null;
            while(reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch(event.getEventType())
                {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        if(tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                        film = new Film();
                        films.add(film);
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if(tagType.isPresent() && film != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            switch (tagType.get()) {
                                case TITLE -> {
                                    if(!data.isEmpty()) {
                                        film.setTitle(data);
                                    }
                                }
                                case DURATION -> {
                                    if(!data.isEmpty()) {
                                    try {
                                        film.setDuration(Integer.parseInt(data));
                                    } catch (NumberFormatException e) {
                                        Logger.getLogger(FilmParser.class.getName()).log(Level.SEVERE, "Invalid duration: " + data, e);
                                    }
                                    break;                                    }
                                }
                                case PUB_DATE -> {
                                    if(!data.isEmpty()) {
                                        ZonedDateTime zonedDateTime = ZonedDateTime.parse(data, formatter);
                                        
                                        LocalDate publishedDate = zonedDateTime.toLocalDate();
                                        film.setPublishedDate(publishedDate);
                                    }
                                }
                                case DESCRIPTION-> {
                                    if(!data.isEmpty()) {
                                if (!data.isEmpty()) {
                                    film.setDescription(data);

                                    String imageUrl = extractImageUrlFromDescription(data);
                                    if (imageUrl != null) {
                                        handlePicture(film, imageUrl);  
                                        }
                                    }
                                }

                                }
                                /*case ACTORS ->{
                                if(!data.isEmpty()) {
                                handlePersonData(data, Role.ACTOR, film.getId(), people, personId);
                                }
                                }
                                case DIRECTOR -> {
                                if(!data.isEmpty()) {
                                handlePersonData(data, Role.DIRECTOR, film.getId(), people, personId);
                                }
                                }*/
                                case ENCLOSURE ->{
                                    if(startElement != null && film.getPicturePath() == null) {
                                        Attribute urlAttribute = startElement.getAttributeByName(new QName(ATTRIBUTE_URL));
                                        if(urlAttribute != null) {
                                            handlePicture(film, urlAttribute.getValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                                case XMLStreamConstants.END_ELEMENT -> {
                                    if(tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                                    if (film != null) {
                                    if (filmSet.add(film)) {
                                        films.add(film);
                                }
                            }
                        }
                    }
                }
            }
        }
        return films;
    }

    private static void handlePicture(Film film, String pictureUrl) {
        try {
            String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
            if(ext.length() > 4) {
                ext = EXT;
            }
            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;
            
            FileUtils.copyFromUrl(pictureUrl, localPicturePath);
            film.setPicturePath(localPicturePath);
        }
        catch(IOException ex)
        {
            Logger.getLogger(FilmParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static final Pattern IMG_PATTERN = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']");

    
    private static String extractImageUrlFromDescription(String description) {
    String decodedDescription = description.replace("&lt;", "<")
                                           .replace("&gt;", ">")
                                           .replace("&amp;", "&");
    Matcher matcher = IMG_PATTERN.matcher(decodedDescription);
    if (matcher.find()) {
        return matcher.group(1); 
    }
    return null; 
    }

    
    /*    private static void handlePersonData(String data, Role role, int filmId, List<Person> people, int personId) {
    if(!data.isEmpty()) {
    try {
    String names = data.replaceAll("<!\\[CDATA\\[", "").replaceAll("\\]\\]>", "").trim();
    String [] peopleNames = names.split(DEL);
    
    for(String personName : peopleNames) {
    Person person = new Person(personName, role, filmId);
    if(!repository.findPerson(person).isPresent()) {
    person.setId(personId);
    personId++;
    people.add(person);
    }
    }
    }
    catch (Exception ex) {
    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    }*/    

    private enum TagType {
        
        ITEM("item"),
        TITLE("title"),
        DURATION("duration"),
        PUB_DATE("pubDate"),
        DESCRIPTION ("description"),
        ACTORS("actors"),
        DIRECTOR("director"),
        ENCLOSURE("enclosure");
        
        private final String name;

        private TagType(String name) {
            this.name = name;
        }
        
        private static Optional<TagType> from(String name)
        {
            for(TagType value : values()) {
                if(value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
        
    }    
    
}

