package de.uniba.wiai.dsg.ajp.assignment3;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChildrensPriceTest {

    private ChildrensPrice childrensPrice;

    @BeforeEach
    public void setUp() {
        childrensPrice = new ChildrensPrice();
    }


    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 3, 5})
    public void getChargeReturnsPositiveNumber(int daysRented) {
        //ChildrensPrice childrensPrice = new ChildrensPrice();

        assertTrue(childrensPrice.getCharge(daysRented) > 0, "Method getCharge does not return a positive Number with Inputvalue: " + daysRented);
    }


    @Test
    public void getPriceCodeReturnsCorrectNumber() {
        Movie testMovie = Mockito.mock(Movie.class);
        assertEquals(Category.CHILDRENS.getValue(), childrensPrice.getPriceCode(), "Method getPriceCode does not return the correct Number");
    }


}
