package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;

public class CustomerTest {

    private Customer customer;


    @BeforeEach
    private void setUp() {
        customer = new Customer("Bob");
    }

    @AfterEach
    private void tearDown() {
        customer = null;
    }


    @Test
    public void statementInCorrectFormat() {
        // given
        setUpCustomer();
        // when
        //TODO: unnötige String Variable deshalb kein when-Teil nötig???
        //String expected = expectedStatementOutput();

        // then
        assertEquals(expectedStatementOutput(), customer.statement(), "Method statement() does not print in correct format.");

        // tear down
        tearDownMocks();
    }

    @Test
    public void htmlStatementInCorrectForm() {
        // given
        setUpCustomer();

        //then
        assertEquals(expectedHtmlStatementOutput(), customer.htmlStatement(), "Method htmlstatement() does not print in correct format.");

        // tear down

        tearDownMocks();

    }

    @Test
    public void getTotalChargeReturnspPositiveNumber() {
        // given
        setUpCustomer();

        // then
        assertNotEquals(null, customer.getTotalCharge(), "Method getTotalCharge returns null.");
        assertTrue(customer.getTotalCharge() > 0, "Method getTotalCharge returns negative Number.");

        // tear down
        tearDownMocks();
    }

    //TODO: testing INT getTotalFrequentRenterPoints() positive Number >= 0
    @Test
    public void getTotalFrequentRenterPointsReturnsPositiveNumber() {
        setUpCustomer();

        assertTrue(customer.getTotalFrequentRenterPoints() >= 0, "Method getFrequentRenterPoints returns negative Number.");
        tearDownMocks();
    }

    private void setUpCustomer() {
        List<Rental> rentalList = setUpRentalList();
        int i = 0;
        for (Rental each : rentalList) {
            setUpRentalsAndMovies(each, i);
            i++;
        }
        customer.setRentals(rentalList);

    }

    private void tearDownMocks() {
        for (Rental each : customer.getRentals()) {
            reset(each.getMovie());
            reset(each);
        }
    }

    private void setUpRentalsAndMovies(Rental rental, int i) {

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
