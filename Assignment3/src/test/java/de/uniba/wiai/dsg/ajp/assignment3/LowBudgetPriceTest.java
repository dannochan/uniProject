package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LowBudgetPriceTest {
    private Price lowBudgetPrice;
    @BeforeEach
    public void setup() {
        lowBudgetPrice = new LowBudgetPrice();
    }

    @ParameterizedTest
    @MethodSource("differentdaysrented")
    public void getChargerenteddaysreturnexpectedvalue(int daysrented,double expected){

        //given see setup method

        // when
        double result = lowBudgetPrice.getCharge(daysrented);

        // then

        assertEquals(result,expected);



    }

    public static List<Arguments> differentdaysrented(){
        return List.of(Arguments.of(1, 1),
                Arguments.of(2,1.5),
                Arguments.of(4, 2),
                Arguments.of(3,2));
    }



    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -3})
    public void getchargethrowExceptionwhenvalueiszeroornegativ(int daysrented) {
        // given see setup methode

        // when and then
        assertThrows(IllegalArgumentException.class, () -> {
            lowBudgetPrice.getCharge(daysrented);
        });

    }











}
