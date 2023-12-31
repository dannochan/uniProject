package de.uniba.wiai.dsg.ajp.assignment3;

public class ChildrensPrice extends Price {

    @Override
    double getCharge(int daysRented) {
        if (daysRented <= 0) {
            throw new IllegalArgumentException("daysRented must not be negativ or 0");
        }
        double result = 1.5;
        if (daysRented > 3) {
            result += (daysRented - 3) * 1.5;
        }
        return result;
    }

    @Override
    int getPriceCode() {
        return Category.CHILDRENS.getValue();
    }

}
