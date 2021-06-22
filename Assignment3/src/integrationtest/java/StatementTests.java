package de.uniba.wiai.dsg.ajp.assignment3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;


public class StatementTests {
    private Customer customer;


    @Test
    public void statementPrintsCorrectly(){

        customer = new Customer("Bob");
        List<Rental> rentalList = new LinkedList<>();
        Rental rental = new Rental();
        rentalList.add(rental);

        Movie movie = new Movie("Sit", 2, "4K");

        rental.setMovie(movie);
        rental.setDaysRented(3);
        rental.setDiscount(20);

        customer.setRentals(rentalList);
        //String newOne = customer.statement();
        double hansi = rental.getDiscountedCharge();
        assertEquals(expectedOutput(), customer.statement(), "Methode statement prints correctly");


    }

    private String expectedOutput(){
        String resultStatement = "Rental Record for Bob" + "\n";
        resultStatement += "\t" + "Sit (4K)" + "\t" + "3.5" + "\t" + "20" + "\t" + "2.8" + "\n";
        resultStatement += "Amount owed is 2.8" + "\n";
        resultStatement += "You earned 1 frequent renter points";
        return resultStatement;
    }

}
