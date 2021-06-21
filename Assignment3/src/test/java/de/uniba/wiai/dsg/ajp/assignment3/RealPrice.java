package de.uniba.wiai.dsg.ajp.assignment3;

public class RealPrice extends Price {
    @Override
    double getCharge(int daysRented) {
        return 0;
    }

    @Override
    int getPriceCode() {
        return 0;
    }
}
