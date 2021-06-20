package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("Bob");

    }

    @Test
     public void giveBackTheNameOfTheCustomer(){

         //given
       //  Customer customer = new Customer()

         //when
         String outputName = customer.getName();

         //then
         assertEquals("Bob", outputName, "The name should be Bob");

     }


    @Test
    @Disabled
    void setRentals() {
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

        testList.add(rental);

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
