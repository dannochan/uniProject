package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.nio.file.Path;


public class DatabaseServiceImpl implements DatabaseService {

    private Database database;

    DatabaseServiceImpl() {

    }

    DatabaseServiceImpl(Database database) {
        this.database = database;
    }

    DatabaseServiceImpl(List<Database> databases) {

    }

    @Override
    public void addPublication(String title, int yearPublished, PublicationType type, List<String> authorIDs, String id)
            throws LiteratureDatabaseException {

        //TODO: Exceptions einfügen, Email Validierung... Fehler: Wenn Author noch nicht existiert usw.

        Publication freshPublication = new Publication();

        freshPublication.setId(id);
        freshPublication.setTitle(title);
        freshPublication.setYearPublished(yearPublished);
        freshPublication.setType(type);

        //umwandeln der Autorenliste, String to Obj.
        List<Author> currentAuthors = getAuthorObjRefFormStringList(authorIDs);
        freshPublication.setAuthors(currentAuthors);


        //einfuegen der neuen Publication in die Liste der Publicationen (der aktuellen Datenbank)
        getPublications().add(freshPublication);

        //neue Publication bei Autoren eintragen
        ListIterator<Author> currentAuthorIterator = currentAuthors.listIterator();
        while (currentAuthorIterator.hasNext()) {
            currentAuthorIterator.next().getPublications().add(freshPublication);
        }
    }

    private List<Author> getAuthorObjRefFormStringList(List<String> authorsIDs) {

        //empty Result-List to be returned
        List<Author> currentAuthors = new LinkedList<>();

        //getting List-Iterators for while-loop
        ListIterator<Author> allAuthorsIterator = getAuthors().listIterator();
        ListIterator<String> stringAuthorIterator = authorsIDs.listIterator();

        while (allAuthorsIterator.hasNext()) {
            //get current IDs
            String allAuthorID = allAuthorsIterator.next().getId();
            String stringAuthorID = stringAuthorIterator.next();

            if (allAuthorID.equals(stringAuthorID)) {
                //in currentAuthor abspeichern
                Author authorRef = allAuthorsIterator.previous();
                currentAuthors.add(authorRef);
                allAuthorsIterator.next();

            } else {
                stringAuthorIterator.previous();
            }
        }

        //TODO: wenn Author noch nicht in database vorhanden dann neuen anlegen. Implementierung  als if statement in while Schleife oben

        return currentAuthors;

    }

    @Override
    public void removePublicationByID(String id) throws LiteratureDatabaseException {
        //aus Liste der Publicationen entfernen

        List<Publication> deleteHelper = getPublications();
        ListIterator<Publication> deleteHelperIterator = deleteHelper.listIterator();
        List<Author> ListOfPublicationAuthors = new LinkedList<>();

        //get current Publication ID
        String deleteID = deleteHelperIterator.next().getId();

        boolean condition = true;

        while (condition) {
            if (id.equals(deleteID)) {

                // Liste der Autoren die mit der Publikation zu tun haben
                ListOfPublicationAuthors = deleteHelperIterator.previous().getAuthors();

                //zuruecksetzen des Iterators zum nachfolgenden loeschen
                deleteHelperIterator.next();

                //Loeschen der Publication aus Liste
                deleteHelperIterator.remove();


                //Schleife abbrechen
                condition = false;
            } else {
                //Schleife abbrechen, wenn die Liste aller Publications zu Ende ist
                if (!deleteHelperIterator.hasNext()) {
                    condition = false;
                } else {
                    deleteID = deleteHelperIterator.next().getId();
                }
            }
        }


        //<Publication aus Autoren-Obj. entfernen

        //shows the current Author-Obj., where to search for the Publication to be removed
        ListIterator<Author> loPAIterator = ListOfPublicationAuthors.listIterator();

        //wenn die ListOfPublicationAuthors leer ist funkt. der Iterator nicht
        if(!ListOfPublicationAuthors.isEmpty()){
        //re-use of deleting Helper Var., shows the Obj. to be removed
        deleteHelperIterator = loPAIterator.next().getPublications().listIterator();
        loPAIterator.previous();}

        while (loPAIterator.hasNext()) {
            condition = true;
            //neudeklatration der Helper-Var., da LoPAIterator auf andere Stelle zeigt (geht nicht ohne)
            deleteHelperIterator = loPAIterator.next().getPublications().listIterator();
            loPAIterator.previous();
            //auslesen der aktuellen Id in der Liste
            deleteID = deleteHelperIterator.next().getId();

            while (condition) {
                if (id.equals(deleteID)) {
                    //Loeschen der Publication aus Liste
                    deleteHelperIterator.remove();
                    //Schleife abbrechen
                    condition = false;
                } else {
                    //Schleife abbrechen, wenn die Liste aller Publications zu Ende ist
                    if (!deleteHelperIterator.hasNext()) {
                        condition = false;
                    } else {
                        deleteID = deleteHelperIterator.next().getId();
                    }
                }
            }
            loPAIterator.next();
        }


    }

    @Override
    public void removeAuthorByID(String id) throws LiteratureDatabaseException {

        //aus Liste der Autoren löschen
        List<Author> deleteHelper = getAuthors();
        ListIterator<Author> deleteHelperIterator = deleteHelper.listIterator();
        List<Publication> ListofAuthorsPublications = new LinkedList<>();

        //get current Author ID
        String deleteID = deleteHelperIterator.next().getId();

        boolean condition = true;

        while (condition) {
            if (id.equals(deleteID)) {

                // Liste der Publications von loeschenden Author merken
                ListofAuthorsPublications = deleteHelperIterator.previous().getPublications();

                //zuruecksetzen des Iterators um zu loeschen
                deleteHelperIterator.next();

                //Loeschen des Authors aus Liste
                deleteHelperIterator.remove();


                //Schleife abbrechen
                condition = false;
            } else {
                //Schleife abbrechen, wenn die Liste aller Authors zu Ende ist
                if (!deleteHelperIterator.hasNext()) {
                    condition = false;
                } else {
                    deleteID = deleteHelperIterator.next().getId();
                }
            }
        }

        //Autoren aus Publication-Objkten entfernen

        ListIterator<Publication> loAPIterator = ListofAuthorsPublications.listIterator();

        //wenn die Liste leer ist funkt. der Iterator nicht
        if (!ListofAuthorsPublications.isEmpty()) {
            //re-use of deleteHelper-Var., shows the Obj. to be removed
            deleteHelperIterator = loAPIterator.next().getAuthors().listIterator();
            loAPIterator.previous();
        }

        while (loAPIterator.hasNext()) {
            condition = true;
            //neudeklatration der Helper-Var., da LoAPIterator auf andere Stelle zeigt (geht nicht ohne)
            deleteHelperIterator = loAPIterator.next().getAuthors().listIterator();
            loAPIterator.previous();
            //auslesen der aktuellen Id in der Liste
            deleteID = deleteHelperIterator.next().getId();

            while (condition) {
                if (id.equals(deleteID)) {
                    //Loeschen des Author aus Liste
                    deleteHelperIterator.remove();
                    //Schleife abbrechen
                    condition = false;
                } else {
                    //Schleife abbrechen, wenn die Liste aller Authors zu Ende ist
                    if (!deleteHelperIterator.hasNext()) {
                        condition = false;
                    } else {
                        deleteID = deleteHelperIterator.next().getId();
                    }
                }
            }
            loAPIterator.next();
        }

    }

    @Override
    public void addAuthor(String name, String email, String id) throws LiteratureDatabaseException {

        Author newAuthor = new Author();
        newAuthor.setName(name);
        newAuthor.setEmail(email);
        newAuthor.setId(id);

        //TODO: Eingabedaten ueberpruefen, wo? Author-Setter oder hier?

        List<Author> inputHelper = getAuthors();
        inputHelper.add(newAuthor);

    }

    @Override
    public List<Publication> getPublications() {

        return this.database.getPublications();
    }

    @Override
    public List<Author> getAuthors() {

        return this.database.getAuthors();
    }

    @Override
    public void clear() {
        List<Publication> publications = getPublications();
        publications.clear();
        List<Author> authors = getAuthors();
        authors.clear();
    }

    @Override
    public void printXMLToConsole() throws LiteratureDatabaseException {
        try {
            JAXBContext context = JAXBContext.newInstance(Database.class);
            Marshaller ms = context.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ms.marshal(database, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

    }

}
