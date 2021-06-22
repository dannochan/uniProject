package de.uniba.wiai.dsg.ajp.assignment3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieTest {

    private Movie movie;


/*
    @ParameterizedTest
    @MethodSource("differentPictureQuality")
    public void getChargereturncorrectvalue(PictureQuality picturequality, double expected) {
        //given

        Movie movie = new Movie("Titan",3, picturequality);

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
    public void getChargeThrowExceptionnonvalideinput(int daysRented) {
        //given
        movie = new Movie();

        // when and then
        assertThrows(IllegalArgumentException.class, () -> {
            movie.getCharge(daysRented);
        });


    }

*/


}
