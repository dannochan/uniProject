package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        tearDownMocks();
        customer = null;

    }


    @Test
    public void statementInCorrectFormat() {
        
        setUpCustomer();

        String testStatement = customer.statement();

        assertEquals(expectedStatementOutput(), testStatement, "Method statement() does not print in correct format.");
    }

    @Test
    public void htmlStatementInCorrectForm() {

        setUpCustomer();

        String outputTest = customer.htmlStatement();

        assertEquals(expectedHtmlStatementOutput(), outputTest, "Method htmlstatement() does not print in correct format.");

    }

    @Test
    public void getTotalChargeReturnspPositiveNumber() {

        setUpCustomer();

        double chargeTest = customer.getTotalCharge();

        assertNotEquals(null, chargeTest, "Method getTotalCharge returns null.");
        assertTrue(chargeTest > 0, "Method getTotalCharge returns negative Number.");

    }

    @Test
    public void getTotalFrequentRenterPointsReturnsPositiveNumber() {

        setUpCustomer();

        double pointTest = customer.getTotalFrequentRenterPoints();

        assertTrue(pointTest >= 0, "Method getFrequentRenterPoints returns negative Number.");

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
                given(rental.getDiscount()).willReturn(20);
                given(rental.getDiscountedCharge()).willReturn(3.0);
                break;
            case 1:
                given(movieMock.getTitle()).willReturn("Feuerzangenbowle");
                given(rental.getCharge()).willReturn(5.0);
                given(rental.getDiscount()).willReturn(25);
                given(rental.getDiscountedCharge()).willReturn(4.0);
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

/*        String resultStatement = "";
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("src/test/expectedStatementOuput"), StandardCharsets.UTF_8);
            String readerInput = reader.readLine();
            while (readerInput != null) {
                resultStatement += readerInput;
                readerInput = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
            resultStatement = null;

        }
        return resultStatement;*/


        String resultStatement = "Rental Record for Bob" + "\n";
        resultStatement += "\t" + "Harry Potter" + "\t" + "4.0" + "\t" + "20" + "\t" + "3.0" + "\n";
        resultStatement += "\t" + "Feuerzangenbowle" + "\t" + "5.0" + "\t" + "25" + "\t" + "4.0" + "\n";
        resultStatement += "Amount owed is 7.0" + "\n";
        resultStatement += "You earned 2 frequent renter points";
        return resultStatement;
    }

    private String expectedHtmlStatementOutput() {

        String resultHtmlStatement = "<H1>Rentals for <EM>Bob" + "</EM></H1><P>\n";
        resultHtmlStatement += "Harry Potter: " + "4.0 20 3.0" + "<BR>\n";
        resultHtmlStatement += "Feuerzangenbowle: " + "5.0 25 4.0" + "<BR>\n";
        resultHtmlStatement += "<P>You owe <EM>7.0" + "</EM><P>\n";
        resultHtmlStatement += "On this rental you earned <EM>2" + "</EM> frequent renter points<P>";
        return resultHtmlStatement;
    }

}
