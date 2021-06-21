package de.uniba.wiai.dsg.ajp.assignment3;

public class LowBudgetPrice extends Price {
    @Override
    double getCharge(int daysRented) {

        if (daysRented <= 0) {
            throw new IllegalArgumentException("The given number ist not valid! Please try again!");
        }

        double result = 1.5;
        if (daysRented == 1) {
            result = 1;
        } else if (daysRented == 2) {
            result = 1.5;
        } else {
            int index = 3;
            while (index <= daysRented) {
                result += 0.5;
            }
        }
        return result;
    }

    @Override
    int getPriceCode() {
        return Category.LOW_BUDGET.getValue();
    }
}
