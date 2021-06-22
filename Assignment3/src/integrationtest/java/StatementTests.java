package de.uniba.wiai.dsg.ajp.assignment3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;


public class StatementTests {
    private Customer customer;
    private Rental rental;
    private Movie movie;

    @BeforeEach
    private void setUp() {
        customer = new Customer("Bob");
        List<Rental> rentalList = new LinkedList<>();
        rental = new Rental();
        rentalList.add(rental);

        movie = new Movie("Sit", 2, "4K");

        rental.setMovie(movie);
        rental.setDaysRented(3);
        rental.setDiscount(20);

        customer.setRentals(rentalList);
    }

    @AfterEach
    private void tearDown() {
        customer = null;
        rental = null;
        movie = null;
    }


    @Test
    public void statementWorksCorrectly() {
        assertEquals(expectedOutput(), customer.statement(), "Methode statement prints correctly");
    }

    @Test
    public void htmlStatementWorksCorrectly() {
        assertEquals(expectedHtmlOutput(), customer.htmlStatement(), "Methode statement prints correctly");
    }

    private String expectedOutput() {
        String resultStatement = "Rental Record for Bob" + "\n";
        resultStatement += "\t" + "Sit (4K)" + "\t" + "3.5" + "\t" + "20" + "\t" + "2.8" + "\n";
        resultStatement += "Amount owed is 2.8" + "\n";
        resultStatement += "You earned 1 frequent renter points";
        return resultStatement;
    }

    private String expectedHtmlOutput() {

        String resultHtmlStatement = "<H1>Rentals for <EM>Bob" + "</EM></H1><P>\n";
        resultHtmlStatement += "Sit (4K): " + "3.5 20 2.8" + "<BR>\n";
        resultHtmlStatement += "<P>You owe <EM>2.8" + "</EM><P>\n";
        resultHtmlStatement += "On this rental you earned <EM>1" + "</EM> frequent renter points<P>";
        return resultHtmlStatement;
    }

}
