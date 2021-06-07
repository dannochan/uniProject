package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.MainService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.ui.ConsoleHelper;

import java.io.IOException;


public class ConsoleUI {

    private ConsoleHelper consoleHelper;

    private boolean exit;

    private MainService newDB;

    private DatabaseService newDBS;

    private int menuIndex;

    private DatabaseService databaseNew;

    public ConsoleUI() {

        // allerzuerst eine Variable von ConsolHelper Klasse deklarieren, damit das Prog. mit Benutzern interagieren kann

        consoleHelper = ConsoleHelper.build(); // diese Funktion von ConsoleHelper liest Datein von User ein.

        exit = false;       // ein Bedingungsvariable

        newDB = new MainServiceImpl();

        menuIndex = 1;
    }

    //Hauptmenü ausgeben
    public void printMainmenu() {
        System.out.println("(1) Load and Validate Literature Database");
        System.out.println("(2) Create New Literature Database");
        System.out.println("(3) Exit System");
    }

    //Untermenü ausgeben
    public void printSubmenu() {
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
            if (menuIndex==1){
                printMainmenu();
            }else {
                printSubmenu();
            }
            int option = readOption();
            evalOption(option);
        }
    }

    // In dieser Funktione wird nach einer Eingabe gefragt und validiert.
    private int readOption() {

        int result = 9;

        try {
            result = consoleHelper.askIntegerInRange("Please choose an option,", 0, 8);

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
                int newOptionA = readOption();
                evalSubMenuOption(newOptionA);
                break;
            case 2:
                createNewDatabase();
                printSubmenu();
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
                menuIndex = 0;
                break;
            case 2:
                removeAuthor();
                menuIndex = 0;
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
            case 9:
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
        System.out.println("You made it!!!!!!!!!!!!!!!!");
    }

    private void removeAuthor() {

    }

    private void addPublication() {

    }

    private void removePublication() {

    }

    private void listAuthor() {

    }

    private void listPublications() {

    }

    private void printXML() {

    }

    private void saveXmlFile() {

    }

    private void backToMain() {
        menuIndex =1;
    }


    // ein Method, um das Menü zu schließen
    private void closeSystem() {
        exit = true;
        System.exit(0);
    }


}
