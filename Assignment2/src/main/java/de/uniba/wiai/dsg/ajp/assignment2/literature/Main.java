package de.uniba.wiai.dsg.ajp.assignment2.literature;


import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.MainService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl.MainServiceImpl;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // TODO startet eure Anwendung ueber diese main-Methode
        MainService startIt = new MainServiceImpl();
        try {

            DatabaseService mydatabase = startIt.load("database.xml");

            mydatabase.addAuthor("Hans Petter Oechsler", "demhansiseiadress", "a0");
            mydatabase.addAuthor("Hans Petter Oechsler", "demhansiseiadress", "a1");
            mydatabase.addAuthor("Erwin Schnuerschuh","demerwinseiadress","a2");

            List<String> liste = new LinkedList<>();
            liste.add("a1");
            liste.add("a2");
            mydatabase.addPublication("Husten hat er der Hansi", 2020, PublicationType.BOOK, liste, "p1");
            mydatabase.addPublication("Gartensachen", 2021, PublicationType.BOOK, liste, "p2");

            mydatabase.removePublicationByID("p2");
            mydatabase.printXMLToConsole();
            mydatabase.saveXMLToFile("resultFile.xml");

            for (Author author:mydatabase.getAuthors()
                 ) {
                System.out.println(author.getName());
            }

            for (Publication publication : mydatabase.getPublications()
            ) {
                System.out.println(publication.getTitle());
            }

            //mydatabase.clear();
            //mydatabase.printXMLToConsole();

        } catch (LiteratureDatabaseException e) {
            System.out.println("Schrottbude");
        }



    }

}
