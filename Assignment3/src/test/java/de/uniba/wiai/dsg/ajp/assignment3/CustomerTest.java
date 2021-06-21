package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("Bob");

    }


    @Test
    void statementInCorrectFormat() {
        // given

        List<Rental> rentalList = setUpRentalList();
        int i = 0;
        for (Rental rentals : rentalList) {
            setUpRentals(rentals, i);
            i++;
        }
        customer.setRentals(rentalList);

        // then
        assertEquals(expectedStatementOutput(), customer.statement(), "Schrott is schrott");

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

    @Test

    void htmlStatementShouldBeGivenOutInCorrectForm() {
        // given
        List<Rental> testList = new LinkedList<>();

        Movie movieMocke = mock(Movie.class);
        when(movieMocke.getTitle()).thenReturn("The End of the fxxking world");
        when(movieMocke.getPriceCode()).thenReturn(1);
        when(movieMocke.getCharge(3)).thenReturn(23.99);

        Rental rental = mock(Rental.class);
        when(rental.getMovie()).thenReturn(movieMocke);
        when(rental.getDaysRented()).thenReturn(3);
        when(rental.getCharge()).thenReturn(23.99);

        testList.add(rental);
        customer.setRentals(testList);

        //when

        String resultTest = customer.htmlStatement();
        System.out.println(resultTest);

        // then

    }
}
