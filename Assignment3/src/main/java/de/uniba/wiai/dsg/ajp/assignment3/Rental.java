package de.uniba.wiai.dsg.ajp.assignment3;

public class Rental {

    private int daysRented;
    private Movie movie;
    private int discount = 0;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(int daysRented) {
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

    public void setDiscount(int discount) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Invalid discount, please try again!");
        }
        this.discount = discount;
    }

    public int getDiscount() {
        return this.discount;
    }

    public double getDiscountAmount() {
        return movie.getCharge(daysRented) * (getDiscount() / 100);
    }

    public double getDiscountedCharge(){
        return this.getCharge()-this.getDiscountAmount();
    }

}
