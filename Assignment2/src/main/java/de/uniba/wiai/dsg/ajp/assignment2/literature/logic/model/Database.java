package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "database")
public class Database {

    private List<Author> authors = new LinkedList<>();
    private List<Publication> publications = new LinkedList<>();

    public Database() {
        super();
    }

    @XmlElement(name = "author", required = false)
    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @XmlElement(name = "publication", required = false)
    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

}
