package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceTest {
    private Price price;

    @ParameterizedTest
    @ValueSource(ints ={1,2,3,4,5,6} )
    public void getFrequentrentedpointsforpriceclass(int daysrented){
        //given
        price = new RealPrice();
        // when
        int result = price.getFrequentRenterPoints(daysrented);
        // then
        assertEquals (1,result);

    }
    @ParameterizedTest
    @ValueSource(ints = { -1, -2, -3,0})
    public void getchargethrowExceptionwhenvalueiszeroornegativ ( int daysrented){
        // given
        price = new RealPrice();
        // when and then
        assertThrows(IllegalArgumentException.class, () -> {
            price.getFrequentRenterPoints(daysrented);
        });

    }





}
