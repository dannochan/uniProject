package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;


import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import java.util.List;

public class HelpfulMethodValidation {


    public static boolean isAuthorIdUnique(String id, Database db) {
        List<Author> list = db.getAuthors();
        for (Author author : list) {
            if (author.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPublicationIdUnique(String id, Database db) {
        List<Publication> list = db.getPublications();
        for (Publication publication : list) {
            if (publication.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(Object o) {
        boolean check = false;
        if (o.equals(null)) {
            check = true;
        }
        return check;
    }

    public static boolean checksValue(String text) {
        boolean check = false;
        if (text.isEmpty() || text.equals(null)) {
            check = true;
        }
        return check;

    }
}
