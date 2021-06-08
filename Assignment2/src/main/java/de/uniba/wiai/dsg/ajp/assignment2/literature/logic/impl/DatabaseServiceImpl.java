package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseProvider;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.ValidationHelper;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.nio.file.Path;
import java.util.stream.Collectors;


public class DatabaseServiceImpl implements DatabaseService {

    private Database database;

    DatabaseServiceImpl() {
    }

    DatabaseServiceImpl(Database database) {
        this.database = database;
        DatabaseProvider.setDbInstance(this.database);
    }


    @Override
    public void addPublication(String title, int yearPublished, PublicationType type, List<String> authorIDs, String id)
            throws LiteratureDatabaseException {

        //TODO: Eingabedaten (Title, yearPublished, type und ID) ueberpruefen -> Validator!
        //TODO: Wenn Author noch nicht in databse vhd.-

        HelpfulMethodValidation helpfulMethodValidation = new HelpfulMethodValidation();
        // validate title
        if (!helpfulMethodValidation.checksValue(title)) {
            throw new LiteratureDatabaseException("Invalid publication title.");
        }

        // validate year
        if (yearPublished < 0) {
            throw new LiteratureDatabaseException("Invalid publication year.");
        }

        // validate type
        if (helpfulMethodValidation.isNull(type)) {
            throw new LiteratureDatabaseException("Invalid publication type.");
        }

        // validate authors
        if (helpfulMethodValidation.isNull(authorIDs) || authorIDs.isEmpty()) {
            throw new LiteratureDatabaseException("Invalid publication author.");
        } else {
            // remove duplicate author Ids
            authorIDs = authorIDs.stream()
                    .distinct()
                    .collect(Collectors.toList());
        }

        // validate id
        if (!ValidationHelper.isId(id)) {
            throw new LiteratureDatabaseException("Invalid publication id.");
        }
        if (!helpfulMethodValidation.isPublicationIdUnique(id)) {
            throw new LiteratureDatabaseException("Publication's id must be unique.");
        }





        // Abfrage der Autoren, ob bereits in DB hinter
        // + Umwandlung der Autoren IDs in eine List<Author>, da diese zum Konstruktor einer Publication benötigt wird
        List<Author> authors = new ArrayList<>(); //code in andys code rein
        for (String authorID : authorIDs) {
            Author author = Author.getAuthorByID(authorID, this.database);
            if (author != null) {
                authors.add(author);
            } else {
                throw new LiteratureDatabaseException("Author with ID: " + authorID + " does not exist!");
            }
        }
        //Erzeugung der Publication und Hinzufügen in DB
        Publication newPub = new Publication(id, title, yearPublished, type, authors);
        updateAuthorsListWithNewPublication(authors, newPub);
        database.getPublications().add(newPub);
    }


    @Override
    public void removePublicationByID(String id) throws LiteratureDatabaseException {


        //TODO: Eingabedaten (Email und ID) ueberpruefen -> Validator
        // validate id
        if (!ValidationHelper.isId(id)) {
            throw new LiteratureDatabaseException("Invalid publication's id.");
        }

        // Publication wird anhand der ID in der Datenbank gesucht, nach dem Löschvorgang wird die Publication /n
        // Liste aktualisiert
        Publication pbToBeRemoved = Publication.getPublicationByID(id, this.database);
        if (pbToBeRemoved != null) {
            List<Author> authorsOfPubsToBeRemoved = pbToBeRemoved.getAuthors(); //Suchvorgang
            database.getPublications().remove(pbToBeRemoved); //Löschvorgang in DB
            updatePublicationListAfterRemovedPublication(authorsOfPubsToBeRemoved, pbToBeRemoved); //Aktualisierung der Publicationen + Löschen der Publication noch bei den einzelnen Verfassern/Autoren
        } else {
            throw new LiteratureDatabaseException("Publication not found, check ID"); //Exception, sollte Publication nicht gefunden werden
        }

    }

    @Override
    public void removeAuthorByID(String id) throws LiteratureDatabaseException {

        //TODO: Eingabedaten (ID) ueberpruefen -> Validator
        // validate id
        if (!ValidationHelper.isId(id)) {
            throw new LiteratureDatabaseException("Invalid publication's id.");
        }

        Author authorToBeRemoved = Author.getAuthorByID(id, this.database); //Suchvorgang nach Autor
        if (authorToBeRemoved != null) {
            List<Publication> pubsOfAuthorToBeRemoved = authorToBeRemoved.getPublications();
            database.getAuthors().remove(authorToBeRemoved); //falls in DB vorhande, entfernen
            updateAuthorListAfterRemovedAuthor(pubsOfAuthorToBeRemoved, authorToBeRemoved);
        } else {
            if (database.getAuthors().isEmpty()) { //falls nicht in DB vorhanden, überprüfen ob DB leer ist oder es den Autor wirklich nicht gibt
                throw new LiteratureDatabaseException("Database does have not any author(s)");
            } else {
                throw new LiteratureDatabaseException("Author not found");
            }
        }
    }

    @Override
    public void addAuthor(String name, String email, String id) throws LiteratureDatabaseException {
        //TODO: Eingabedaten (Email und ID) ueberpruefen -> Validator
        HelpfulMethodValidation helpfulMethodValidation = new HelpfulMethodValidation();


        //validate name

        if (!helpfulMethodValidation.checksValue(name)) {
            throw new LiteratureDatabaseException("Invalid author's name.");
        }
        // validate email
        if (!ValidationHelper.isEmail(email)) {
            throw new LiteratureDatabaseException("Invalid author's id.");
        }

        //validate id
        if (!helpfulMethodValidation.isAuthorIdUnique(id)) {
            throw new LiteratureDatabaseException("author's id must be unique.");
        }



        if (Author.getAuthorByID(id, this.database) == null) {
            Author authorToBeAdded = new Author(id, name, email, new LinkedList<>());
            database.getAuthors().add(authorToBeAdded);
        }
    }

    @Override
    public List<Publication> getPublications() { return this.database.getPublications();}

    @Override
    public List<Author> getAuthors() {return this.database.getAuthors();}

    @Override
    public void clear() {
        database.getPublications().clear();
        database.getAuthors().clear();
    }

    @Override
    public void printXMLToConsole() throws LiteratureDatabaseException {
        try {
            JAXBContext context = JAXBContext.newInstance(Database.class);
            Marshaller ms = context.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ms.marshal(database, System.out);
        } catch (JAXBException e) {
            throw new LiteratureDatabaseException("error during marschalling.");
        }

    }

    @Override
    public void saveXMLToFile(String path) throws LiteratureDatabaseException {
        try {
            JAXBContext context = JAXBContext.newInstance(Database.class);
            Marshaller ms = context.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ms.marshal(database, Path.of(path).toFile());
        } catch (JAXBException e) {
            throw new LiteratureDatabaseException("error during marschalling  to xml file.");
        } catch (InvalidPathException exception) {
            throw new LiteratureDatabaseException("invalid path");
        }


    }

    private void updateAuthorsListWithNewPublication(List<Author> authors, Publication newPub) {
        for (Author author : authors) {
            this.database.getAuthors().stream()
                    .filter(a -> a.getId().equals(author.getId()))
                    .forEach(a -> a.getPublications().add(newPub));
        }
    }

    private void updatePublicationListAfterRemovedPublication(List<Author> authors, Publication delPub) {
        for (Author author : authors) {
            this.database.getAuthors().stream()
                    .filter(a -> a.getId().equals(author.getId()))
                    .forEach(a -> a.getPublications().remove(delPub));
        }
    }

    private void updateAuthorListAfterRemovedAuthor(List<Publication> pubs, Author delAuthor) {
        for (Publication pub : pubs) {
            this.database.getPublications().stream()
                    .filter(a -> a.getId().equals(pub.getId()))
                    .forEach(a -> a.getAuthors().remove(delAuthor));
        }
    }

}//
