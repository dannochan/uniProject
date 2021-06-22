package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieTest {

    private Movie movie;

    @ParameterizedTest
    @MethodSource("differentPictureQuality")
    public void getChargeReturnCorrectValue(PictureQuality picturequality, double expected) {
        //given

        Movie movie = new Movie("Sit", 2,"4K");

        // when
        double result = movie.getCharge(2);

        // then
        assertEquals(result, expected);
    }

    public static List<Arguments> differentPictureQuality() {

        return List.of(Arguments.of(PictureQuality.Resolution_HD, 2),
                Arguments.of(PictureQuality.Resolution_4k, 4));


    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    public void getChargeThrowsExceptionOnInvalideInput(int daysRented) {
        //given
        movie = new Movie("Sit",2,"4K");
        assertThrows(IllegalArgumentException.class, () -> {
            movie.getCharge(daysRented);
        });


    }





}
