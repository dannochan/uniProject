package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("Bob");

    }


    @Test
    void statementShouldBeGivenOutInCorrectFormat() {
        // given
        List<Rental> testList = new LinkedList<>();

        Movie movieMocke = mock(Movie.class);
        when(movieMocke.getTitle()).thenReturn("Harry Potter");
        when(movieMocke.getPriceCode()).thenReturn(1);
        when(movieMocke.getCharge(3)).thenReturn(23.99);


        Rental rental = mock(Rental.class);
        when(rental.getMovie()).thenReturn(movieMocke);
        when(rental.getDaysRented()).thenReturn(3);
        when(rental.getCharge()).thenReturn(23.99);
        when(rental.getFrequentRenterPoints()).thenReturn(1);

        Movie movieMocked1 = mock(Movie.class);
        when(movieMocked1.getTitle()).thenReturn("Harry Potter 2");
        when(movieMocked1.getPriceCode()).thenReturn(1);
        when(movieMocked1.getCharge(3)).thenReturn(23.99);

        Rental rental1 = mock(Rental.class);
        when(rental1.getMovie()).thenReturn(movieMocked1);
        when(rental1.getDaysRented()).thenReturn(3);
        when(rental1.getCharge()).thenReturn(23.99);
        when(rental1.getFrequentRenterPoints()).thenReturn(1);

        testList.add(rental);
        testList.add(rental1);

        customer.setRentals(testList);

        //then

        String resultTest = customer.statement();

        System.out.println(resultTest);

        // then

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

        //then

        String resultTest = customer.htmlStatement();
        System.out.println(resultTest);

        // then

    }
}
