package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    private Movie movie;
/*
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
*/
    @BeforeEach
    private void setUp(){
        movie = new Movie("Sit",2,"4K");
    }

    @AfterEach
    private void tearDown(){
        movie = null;
    }

    @ParameterizedTest
    @ValueSource(ints={1,2,5,10})
    public void getChargeReturnsPositveNumber(int daysRented){
        double result = movie.getCharge(daysRented);
        assertTrue(result>0,"Method getCharge returns negative Number.");

    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    public void getChargeThrowsExceptionOnInvalideInput(int daysRented) {
        assertThrows(IllegalArgumentException.class, () -> {
            movie.getCharge(daysRented);
        });
    }

    @Test
    public void getTitleInCorrectFormat(){
        assertEquals("Sit (4K)",movie.getTitle(), "Method getTitle() does not print in correct Format.");

    }





}
