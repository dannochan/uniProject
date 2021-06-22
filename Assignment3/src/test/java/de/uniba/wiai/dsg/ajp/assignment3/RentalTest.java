package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RentalTest {

    private Rental rental;

    @BeforeEach
    void setUp() {
        rental = new Rental();
    }


    @Test
    void giveOutTheFrequentRenterPointsOfAMovie() {
        //given
        Movie movie = mock(Movie.class);
        given(movie.getPriceCode()).willReturn(0);
        given(movie.getFrequentRenterPoints(4)).willReturn(2);
        rental.setMovie(movie);
        rental.setDaysRented(4);
        //when
        int pointsOutput = rental.getFrequentRenterPoints();
        //then
        assertEquals(2, pointsOutput, "Customer get 2 renter points for " + rental.getDaysRented() + "days");

    }


    @Test
    void getDiscountAmount() {
        //given
        rental.setDiscount(20);
        Movie movie = mock(Movie.class);
        given(movie.getCharge(4)).willReturn(10.0);
        rental.setMovie(movie);

        //when
        double discountTest = rental.getDiscountAmount();

        //then
        assertEquals(2, discountTest, "The discount is not correct!");

    }

    @Test
    void getDiscountedCharge() {
    }
}