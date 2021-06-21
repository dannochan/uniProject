package de.uniba.wiai.dsg.ajp.assignment3;

public abstract class Price {

	abstract double getCharge(int daysRented);

	int getFrequentRenterPoints(int daysRented) {
		if (daysRented<=0){
			throw new IllegalArgumentException("The given number ist not valid! Please try again!");
		}


		return 1;
	}

	abstract int getPriceCode();

}
