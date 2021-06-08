package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.MainService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;
import de.uniba.wiai.dsg.ajp.assignment2.literature.ui.ConsoleHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class ConsoleUI {

    private ConsoleHelper consoleHelper;

    private boolean exit;

    private MainService newDB;

    private boolean MAINMENU;

    private DatabaseService databaseNew;

    public ConsoleUI() {

        // allerzuerst eine Variable von ConsolHelper Klasse deklarieren, damit das Prog. mit Benutzern interagieren kann

        consoleHelper = ConsoleHelper.build(); // diese Funktion von ConsoleHelper liest Datein von User ein.

        exit = false;       // ein Bedingungsvariable

        newDB = new MainServiceImpl();

        MAINMENU = true;
    }

    //Hauptmenü ausgeben
    private void printMainmenu() {
        System.out.println("(1) Load and Validate Literature Database");
        System.out.println("(2) Create New Literature Database");
        System.out.println("(3) Exit System");
    }

    //Untermenü ausgeben
    private void printSubmenu() {
        System.out.println(" (1) Add Author ");
        System.out.println(" (2) Remove Author");
        System.out.println(" (3) Add Publication");
        System.out.println(" (4) Remove Publication");
        System.out.println(" (5) List Authors");
        System.out.println(" (6) List Publication");
        System.out.println(" (7) Print XML on Console");
        System.out.println(" (8) Save XML to File");
        System.out.println(" (0) Back to main menu / close without saving");
    }

    // when exit nicht falsch ist, wird die
    public void startReadEvaPrint() throws LiteratureDatabaseException {
        while (!exit) {
            if (MAINMENU) {
                printMainmenu();

                int option = readOption();
                evalOption(option);
            } else {
                printSubmenu();

                int option = readOption();
                evalSubMenuOption(option);
            }

            // evaluate von Untermenü (auch hier einpacken, mithilfe If-Schleife? )

        }
    }

    // In dieser Funktione wird nach einer Eingabe gefragt und validiert.
    private int readOption() {

        int result = 9;

        try {
            if (MAINMENU) {
                result = consoleHelper.askIntegerInRange("Please choose an option,", 1, 3);
                while(true){
                    try{
                        Scanner scan = new Scanner();
                        scan.

                    }catch (LiteratureDatabaseException e){
                        e.
                        continue;
                    }
                }
            } else {
                result = consoleHelper.askIntegerInRange("Please choose an option,", 0, 8);
            }

            // was hier noch gerne hätte, ist, nachdem die Fehlermeldung auskommt, wird nochmal nach Eingabe gefragt.

        } catch (IOException ioException) {
            System.err.println("input is not valid!");
            readOption();
        }

        return result;
    }

    // Je nach Eingabe der User, wirde verischieden Funktionen aufgerufen.
    private void evalOption(int input) throws LiteratureDatabaseException {

        switch (input) {
            case 1:
                loadAndValidateDatabase();
                printSubmenu();
                MAINMENU = false;
                int newOptionA = readOption();
                evalSubMenuOption(newOptionA);
                break;
            case 2:
                createNewDatabase();
                printSubmenu();
                MAINMENU = false;
                int newOptionB = readOption();
                evalSubMenuOption(newOptionB);
                break;
            case 3:
                closeSystem();
                break;

        }

    }

    private void evalSubMenuOption(int input) {

        switch (input) {
            case 1:
                addAuthor();
                break;
            case 2:
                removeAuthor();
                break;
            case 3:
                addPublication();
                break;
            case 4:
                removePublication();
                break;
            case 5:
                listAuthor();
                break;
            case 6:
                listPublications();
                break;
            case 7:
                printXML();
                break;
            case 8:
                saveXmlFile();
                break;
            case 0:
                backToMain();
                break;
            default:
                break;

        }
    }

    // Hier werden die Methode von MainServiceImpl aufgerufen -> create oder load

    private void createNewDatabase() throws LiteratureDatabaseException {
        System.out.println("New Database is created!");
        this.databaseNew = newDB.create();

    }

    private void loadAndValidateDatabase() throws LiteratureDatabaseException {
        System.out.println("New Database is loaded!");
        this.databaseNew = newDB.load("database.xml");
    }

    // die Methode, die der Klasse DatebaseService gehören und beim Untermenü aufgerufen werden sollen:

    private void addAuthor() {
        /* was wir hier allerzuerst brauchen, sind die entsprechenden Eingabe für die AddPublication.
         * 1. Name (String)
         * 2. ID (String)
         * 5. Email (String)
         * */
        try {
            System.out.println("Now you can add an author: ");
            String nameNew = consoleHelper.askString("Enter the name of author.");
            String emailNew = consoleHelper.askString("Enter the email of author.");
            String idNew = consoleHelper.askString("Enter the id of author");

            this.databaseNew.addAuthor(nameNew, emailNew, idNew);

        } catch (LiteratureDatabaseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void removeAuthor() {
        // ID (String )nachfragen
        try {
            String idNew = consoleHelper.askString("Now enter the Id of the author you want to delete: ");
            this.databaseNew.removeAuthorByID(idNew);
        } catch (LiteratureDatabaseException e) {
            System.err.println("The Id of author is not valid!");
            System.out.println(e);
            removeAuthor();
        } catch (IOException e) {
            System.err.println("The given data is not valid!");
            e.printStackTrace();
        }

    }

    private void addPublication() {
        /* was wir hier allerzuerst brauchen, sind die entsprechenden Eingabe für die AddPublication.
         * 1. title  (String)
         * 2. yearPublishe (int )
         * 3. PublicationType
         * 4. AuthorsID (List von String)
         * 5. ID (String)
         * */
        try {


            String titleNew = consoleHelper.askNonEmptyString("Please enter the name of the publication!");
            int publishYear = consoleHelper.askInteger("Please enter the year of publication!");

            List<String> newAuthorIDs = enterIDs();

            String newType = consoleHelper.askString("Now enter the type of this publication!");
            PublicationType newPT = PublicationType.valueOf(newType);
            String IdNew = consoleHelper.askNonEmptyString("Please enter the ID of the publication!");

            this.databaseNew.addPublication(titleNew, publishYear, newPT, newAuthorIDs, IdNew);
        }
        // TODO: Bitte hier auch noch Fehler behandeln
        catch (IOException e) {
            e.printStackTrace();
        } catch (LiteratureDatabaseException e) {
            e.printStackTrace();
        }


    }

    // eine Methode, die Eingabe von user in Array abspeichert und in List umwandeln kann
    private List<String> enterIDs() {

        List<String> resultList = new LinkedList<>();
        try {
            System.out.format(" The Author-ID-List (id1, id2, ...) : ");
            String[] inputArray = (consoleHelper.askNonEmptyString("here: ")).split(", ");
            resultList = Arrays.stream(inputArray).collect(Collectors.toList());
            // TODO: Bitte hier auch noch Fehler behandeln
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private void removePublication() {
        try {
            String idNew = consoleHelper.askString("Now enter the Id of the author you want to delete: ");
            this.databaseNew.removePublicationByID(idNew);
        } catch (LiteratureDatabaseException e) {
            System.err.println("The Id of publication is not valid!");
            System.out.println(e);
            removeAuthor();
        } catch (IOException e) {
            System.err.println("The given data is not valid!");
            e.printStackTrace();
        }

    }

    private void listAuthor() {
        System.out.println("************Authors***********");
        for (int i = 0; i < this.databaseNew.getAuthors().size(); i++) {
            System.out.println(this.databaseNew.getAuthors().get(i).getName());
        }
    }

    private void listPublications() {
        System.out.println("************Publications******");
        for (Publication pubIndex : this.databaseNew.getPublications()
        ) {
            System.out.println(pubIndex.toString());
        }
    }

    private void printXML() {
        System.out.println("**********XMl FIle printed**************");
        try {
            this.databaseNew.printXMLToConsole();
        } catch (LiteratureDatabaseException e) {
            System.err.println("Something went wrong during printing!");
            // muss hier noch igendwas gemacht werden, um den Fehler richtig zu beheben
        }

    }

    private void saveXmlFile() {

        System.out.println("**********Save XML to File**************");
        try {
            this.databaseNew.saveXMLToFile("result.xml");
        } catch (LiteratureDatabaseException e) {
            System.err.println("Something went wrong during printing!");
            // muss hier noch igendwas gemacht werden, um den Fehler richtig zu beheben
        }

    }

    private void backToMain() {
        MAINMENU = true;
    }


    // ein Method, um das Menü zu schließen
    private void closeSystem() {
        exit = true;
        System.exit(0);
    }


}
