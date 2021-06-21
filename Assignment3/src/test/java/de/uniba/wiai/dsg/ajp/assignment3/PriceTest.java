package de.uniba.wiai.dsg.ajp.assignment3;

<<<<<<< HEAD
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




=======
public class PriceTest {
    //TODO: PriceTest mit Klasse die von abstrakter Klasse erbt
>>>>>>> 05d49903465906116b0fd43ef29061195098aecb
}
