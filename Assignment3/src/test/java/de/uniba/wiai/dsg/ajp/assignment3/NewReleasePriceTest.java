package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewReleasePriceTest {
    private Price price;

    @BeforeEach
    public void setup() {
        price = new NewReleasePrice();
    }

    @ParameterizedTest
    @MethodSource("differentdaysrented")
    public void getChargedaysrented(int daysrented, double expected) {
        // given see setup methode

        // when
        double result = price.getCharge(daysrented);

        //then
        assertEquals(result, expected);


    }


    public static List<Arguments> differentdaysrented() {

        return List.of(Arguments.of(1, 1.5),
                Arguments.of(2, 6),
                Arguments.of(4, 12));
    }

    @ParameterizedTest
    @MethodSource("differentdaysrentedforNewreleaseclass")
    public void getFrequentRentedpointaboutdaysrented(int daysrented, double expected) {
        //given see setup methode

        //when
        double result = price.getCharge(daysrented);

        //then
        assertEquals(result, expected);


    }


    public static List<Arguments> differentdaysrentedforNewreleaseclass() {

        return List.of(Arguments.of(1, 1),
                Arguments.of(2, 2),
                Arguments.of(4, 2));
    }


    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -3})
    public void getchargethrowExceptionwhenvalueiszeroornegativ(int daysrented) {
        // given see setup methode

        // when and then
        assertThrows(IllegalArgumentException.class, () -> {
            price.getCharge(daysrented);
        });

    }
}//
