package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;

public class CustomerTest {

    private Customer customer;
    private List<Rental> rentalList;
    private int i;

    @BeforeEach
    void setUp() {
        customer = new Customer("Bob");
        rentalList = setUpRentalList();
        i = 0;
        for (Rental rentals : rentalList) {
            setUpRentals(rentals, i);
            i++;
        }
        customer.setRentals(rentalList);

    }


    @Test
    void statementInCorrectFormat() {

        assertEquals(expectedStatementOutput(), customer.statement(), "Method statement() does not print in correct format.");

    }

    @Test
    void htmlStatementInCorrectForm() {

        assertEquals(expectedHtmlStatementOutput(), customer.htmlStatement(), "Method htmlstatement() does not print in correct format.");

    }

    private void setUpRentals(Rental rental, int i) {

        given(rental.getFrequentRenterPoints()).willReturn(1);

        Movie movieMock = mock(Movie.class);
        given(rental.getMovie()).willReturn(movieMock);
        switch (i) {
            case 0:
                given(movieMock.getTitle()).willReturn("Harry Potter");
                given(rental.getCharge()).willReturn(4.0);
                break;
            case 1:
                given(movieMock.getTitle()).willReturn("Feuerzangenbowle");
                given(rental.getCharge()).willReturn(5.0);
                break;
            default:
                given(movieMock.getTitle()).willReturn("Something");
                given(rental.getCharge()).willReturn(1.0);
                break;
        }
    }


    private List<Rental> setUpRentalList() {
        List<Rental> rentalList = new LinkedList<>();
        Rental rentalOne = mock(Rental.class);
        Rental rentalTwo = mock(Rental.class);
        rentalList.add(rentalOne);
        rentalList.add(rentalTwo);
        return rentalList;
    }

    private String expectedStatementOutput() {
        String resultStatement = "Rental Record for Bob" + "\n";
        resultStatement += "\t" + "Harry Potter" + "\t" + "4.0" + "\n";
        resultStatement += "\t" + "Feuerzangenbowle" + "\t" + "5.0" + "\n";
        resultStatement += "Amount owed is 9.0" + "\n";
        resultStatement += "You earned 2 frequent renter points";
        return resultStatement;
    }

    private String expectedHtmlStatementOutput() {
        String resultHtmlStatement = "<H1>Rentals for <EM>Bob" + "</EM></H1><P>\n";
        resultHtmlStatement += "Harry Potter: " + "4.0" + "<BR>\n";
        resultHtmlStatement += "Feuerzangenbowle: " + "5.0" + "<BR>\n";
        resultHtmlStatement += "<P>You owe <EM>9.0" + "</EM><P>\n";
        resultHtmlStatement += "On this rental you earned <EM>2" + "</EM> frequent renter points<P>";
        return resultHtmlStatement;
    }

}
