package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.MainService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.ui.ConsoleHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUI {

    private ConsoleHelper consoleHelper;

    private boolean exit;

    private MainService newDB;

    private DatabaseService newDBS;

    public ConsoleUI(){

       // reader = new BufferedReader(new InputStreamReader(System.in));
        consoleHelper = ConsoleHelper.build();

        exit = false;

        newDB = new MainServiceImpl();
    }

    //Hauptmenü ausgeben
    public void printMainmenu(){
        System.out.println("(1) Load and Validate Literature Database");
        System.out.println("(2) Create New Literature Database");
        System.out.println("(3) Exit System");
    }

    //Untermenü ausgeben
    public void printSubmenu(){
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

    public void startReadEvaPrint() throws LiteratureDatabaseException {
        while (!exit){
            printMainmenu();

                int option = readOption();
                evalOption(option);


        }
    }

    private int readOption() {

        int result = 9;

        try {
            result = consoleHelper.askIntegerInRange("Please choose an option,", 1, 3);

            } catch (IOException ioException) {
            System.err.println("input is not valid!");
            readOption();
        }

        return result;
    }

    private void evalOption(int input) throws LiteratureDatabaseException {

        switch (input){
            case 1:
                loadAndValidateDatabase();
                printSubmenu();
                break;
            case 2:
                createNewDatabase();
                printSubmenu();
                break;
            case 3:
                closeSystem();
                break;
                /*
                switch (nextOption){
                    case 1:
                        addAuthor();
                        break;
                    case 2:
                        removeAuthor();
                        break;
                    case 3:
                        addPublication;
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

                } */
        }

    }

    private void createNewDatabase() throws LiteratureDatabaseException {
        System.out.println("New Database is created!");
        DatabaseService databaseNew =  newDB.create();

    }

    private void loadAndValidateDatabase() throws LiteratureDatabaseException {
        System.out.println("New Database is loaded!");
        DatabaseService databaseNew =  newDB.load("database.xml");
    }




    // ein Method, um das Menü zu schließen
    private void closeSystem(){

        exit = true;

    }


}
