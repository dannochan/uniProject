package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegularPriceTest {
    private Price regularprice;


    @BeforeEach
    public void setup(){

        regularprice = new RegularPrice();
    }

    @ParameterizedTest
    @MethodSource("differentdaysrented")
    public void getChargedaysrentedreturnexpectedvalue(int daysrented, double expected) {
        // given see setup methode

        // when
        double result = regularprice.getCharge(daysrented);

        //then
        assertEquals(result, expected);


    }


    public static List<Arguments> differentdaysrented() {

        return List.of(Arguments.of(1, 2),
                Arguments.of(3,3.5),
                Arguments.of(4, 5));
    }



    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -3})
    public void getchargethrowExceptionwhenvalueiszeroornegativ(int daysrented) {
        // given see setup methode

        // when and then
        assertThrows(IllegalArgumentException.class, () -> {
            regularprice.getCharge(daysrented);
        });

    }


}
