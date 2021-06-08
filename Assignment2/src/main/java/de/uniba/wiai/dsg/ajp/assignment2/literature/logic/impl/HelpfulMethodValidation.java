package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;

import java.util.List;

public class HelpfulMethodValidation {
    Database database;

    public  boolean isAuthorIdUnique(String id) {
        List<Author> list = this.database.getAuthors();
        for (Author author : list) {
            if (author.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPublicationIdUnique(String id) {
        List<Publication> list = this.database.getPublications();
        for (Publication publication : list) {
            if (publication.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    public boolean isNull(Object o) {

        return (o == null);
    }
    public boolean checksValue(String text) {


        return text != "" && text != null;
    }
}//
