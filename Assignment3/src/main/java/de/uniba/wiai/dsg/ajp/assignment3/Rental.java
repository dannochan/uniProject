package de.uniba.wiai.dsg.ajp.assignment3;

public class Rental {

    private int daysRented;
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("movie must not be null");
        }
        this.movie = movie;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(int daysRented) {
        if (daysRented <= 0) {
            throw new IllegalArgumentException("The given number ist not valid! Please try again!");
        }
        this.daysRented = daysRented;
    }

    public double getCharge() {
        return movie.getCharge(daysRented);
    }

    public int getFrequentRenterPoints() {
        if (movie.getPriceCode() == 3) {
            return 0;
        }
        return movie.getFrequentRenterPoints(daysRented);
    }

}
