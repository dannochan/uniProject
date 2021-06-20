package de.uniba.wiai.dsg.ajp.assignment3;

public class Movie {

    private Price price;

    private String title;

    public Movie(String title, int priceCode) {
        this.title = title;
        this.setPriceCode(priceCode);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    double getCharge(int daysRented) {
        return price.getCharge(daysRented);
    }

    public int getPriceCode() {
        return price.getPriceCode();
    }

    public void setPriceCode(int priceCode) {
        switch (priceCode) {
            case 0:
                price = new RegularPrice();
                break;
            case 1:
                price = new NewReleasePrice();
                break;
            case 2:
                price = new ChildrensPrice();
                break;
            case 3:
                price = new LowBudgetPrice();
                break;
            default:
                throw new IllegalArgumentException("Incorrect Price Code");
        }
    }

    public int getFrequentRenterPoints(int daysRented) {
        return price.getFrequentRenterPoints(daysRented);
    }

}
