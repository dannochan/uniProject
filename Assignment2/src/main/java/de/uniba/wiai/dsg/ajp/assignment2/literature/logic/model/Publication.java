package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Publication {

    private String id;
    private String title;
    private int yearPublished;
    private PublicationType type;
    private List<Author> authors = new LinkedList<>();

    public Publication() {
        super();
    }

    public Publication(String id, String title, int yearPublished, PublicationType type, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.yearPublished = yearPublished;
        this.type = type;
        this.authors = authors;
    }

    @XmlAttribute(name = "type", required = true)
    public PublicationType getType() {
        return type;
    }

    public void setType(PublicationType type) {
        this.type = type;
    }

    @XmlAttribute(name = "yearPublished", required = true)
    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    @XmlElement(name = "title", required = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "id", required = true)
    @XmlID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "author", required = true)
    @XmlIDREF
    public List<Author> getAuthors() {
        return authors;
    }


    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] The author(s) %s published %s as a %s in %d", id,
                getAuthorNames(), title, type, yearPublished);
    }

    private String getAuthorNames() {
        StringJoiner result = new StringJoiner(", ");
        for (int i = 0; i < authors.size(); i++) {
            result.add(authors.get(i).getName());
        }
        return result.toString();
    }

    public static Publication getPublicationByID (String id, Database db) {
        return db.getPublications().stream()
                .filter(pub -> pub.getId().equals(id))
                .findFirst()
                .orElse(null);
    }


    public static List<Publication> getPublicationByAuthors (String authorName, Database db) {
        List<Publication> pubs = db.getPublications().stream()
                                    .filter (pub -> pub.getAuthors().contains(authorName))
                                    .collect(Collectors.toList());
        return pubs;
    }

    public static List<Publication> getPublicationByAuthorsID (String authorID, Database db) {
        List<Publication> pubs = db.getPublications().stream()
                .filter (pub -> pub.getAuthors().contains(authorID))
                .collect(Collectors.toList());
        return pubs;
    }

}
