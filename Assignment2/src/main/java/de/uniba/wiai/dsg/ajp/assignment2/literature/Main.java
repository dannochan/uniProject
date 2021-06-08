package de.uniba.wiai.dsg.ajp.assignment2.literature;



import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl.ConsoleUI;


public class Main {

    public static void main(String[] args) throws LiteratureDatabaseException {

        ConsoleUI mainMenu = new ConsoleUI();
        mainMenu.startReadEvaPrint();


    }

}
