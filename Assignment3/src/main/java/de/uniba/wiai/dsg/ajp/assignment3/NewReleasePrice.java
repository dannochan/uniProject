package de.uniba.wiai.dsg.ajp.assignment3;

public class NewReleasePrice extends Price{

	@Override
	double getCharge(int daysRented) {
		if (daysRented <= 0){
			throw new IllegalArgumentException("daysRented must not be negativ or 0");
		}

		return daysRented * 3;
	}

	@Override
	int getFrequentRenterPoints(int daysRented) {
		if (daysRented <= 0){
			throw new IllegalArgumentException("daysRented must not be negativ or 0");
		}
		if(daysRented > 1) {
			return 2;
		} else {
			return 1;
		}
	}

	@Override
	int getPriceCode() {
		return Category.NEW_RELEASE.getValue();
	}
	
}
