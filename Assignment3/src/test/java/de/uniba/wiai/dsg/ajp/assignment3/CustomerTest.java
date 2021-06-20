package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerTest {

    private Customer customer;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;

    @BeforeEach
    void setUp() {
        customer = new Customer("Bob");
        movie1 = new Movie("Hangover", 0);
        movie2 = new Movie("His Dark Material", 1);
        movie3 = new Movie("Harry Potter", 2);
        movie1.getCharge(3);

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
        Rental rental = mock(Rental.class);
        when(rental.getMovie()).thenReturn(movie1);
        when(rental.getDaysRented()).thenReturn(3);
        when(rental.getCharge()).thenReturn(23.1);

        testList.add(rental);
        customer.setRentals(testList);

        //then

        String resultTest = customer.statement();

        String test2 = testList.get(0).getMovie().getTitle();
        String test3 = customer.getRentals().get(0).getMovie().getTitle();
        int test4 = customer.getRentals().get(0).getDaysRented();
        int test5 = customer.getRentals().get(0).getDaysRented();
        double test6 = customer.getRentals().get(0).getCharge();

        System.out.println(test2);
        System.out.println(test3);
        System.out.println(test4);
        System.out.println(test5);
        System.out.println(test6);
        System.out.println(resultTest);

        // then
       // then(rental).should().getMovie().getTitle();
    }

    @Test

    void htmlStatementShouldBeGivenOutInCorrectForm() {
        // given
        List<Rental> testList = new LinkedList<>();
        Rental rental = mock(Rental.class);
        when(rental.getMovie()).thenReturn(movie1);
        when(rental.getDaysRented()).thenReturn(3);
        when(rental.getCharge()).thenReturn(23.1);

        testList.add(rental);
        customer.setRentals(testList);

        //then

        String resultTest = customer.htmlStatement();

        String test2 = testList.get(0).getMovie().getTitle();
        String test3 = customer.getRentals().get(0).getMovie().getTitle();
        int test4 = customer.getRentals().get(0).getDaysRented();
        int test5 = customer.getRentals().get(0).getDaysRented();
        double test6 = customer.getRentals().get(0).getCharge();

        System.out.println(test2);
        System.out.println(test3);
        System.out.println(test4);
        System.out.println(test5);
        System.out.println(test6);
        System.out.println(resultTest);

        // then
        // then(rental).should().getMovie().getTitle();
    }
}
