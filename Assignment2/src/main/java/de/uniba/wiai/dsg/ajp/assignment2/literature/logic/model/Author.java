package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model;

import javax.sound.midi.Soundbank;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Author {

    private String id;
    private String name;
    private String email;
    private List<Publication> publications = new LinkedList<>();

    public Author() {
        super();
    }

    public Author(String id, String name, String email, List<Publication> publications) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.publications = publications;
    }

    @XmlElement(name = "name", required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "email", required = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name = "id", required = true)
    @XmlID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name="publication", required = false)
    @XmlIDREF
    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) published %d publication(s): %s",
                id, name, email, publications.size(), getPublicationIds());
    }

    private String getPublicationIds() {
        StringJoiner result = new StringJoiner(", ");
        for (int i = 0; i < publications.size(); i++) {
            result.add(publications.get(i).getId());
        }
        return result.toString();
    }

    public static Author getAuthorByID (String id, Database db){

      return db.getAuthors().stream()
              .filter(author -> author.getId().equals(id))
              .findFirst()
              .orElse(null);
    }

    public static List<Author> getAuthorsByPublication (String pubID, Database db) {
        List<Author> authors = db.getAuthors().stream()
                .filter(author -> author.getPublications().contains(pubID))
                .collect(Collectors.toList());
        return authors;
    }


}
