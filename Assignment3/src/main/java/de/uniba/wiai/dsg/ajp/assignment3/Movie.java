package de.uniba.wiai.dsg.ajp.assignment3;

/**
 * give the  films that can be rented by a Customer with different prices
 */
public class Movie {

    private Price price;

    private String title;

    private VideoQuality quality;

    public Movie(String title, int priceCode, String quality) {
        this.title = title;
        this.setPriceCode(priceCode);
        this.quality = getQualityType(quality);
    }

    public String getTitle() {
        return title + " (" + quality.getValue() + ")";
    }

    public void setQualilty(String qualiltyValue) {
        this.quality = getQualityType(qualiltyValue);
    }

    public String getQuality() {
        return this.quality.getValue();
    }

    public void setTitle(String title) {

        if (title == null && title == "") {
            throw new IllegalArgumentException("name must not be null or empty.");
        }

        this.title = title;
    }

    /**
     * Gets charge for each film according to its number of rental days
     * <p>
     * precondition:
     * <ul>
     * <li> Movie must not be null.</li>
     * <li> The days rented must be greater than 0.</li>
     * </ul>
     * <p>
     * postcondition:
     * <ul>
     * <li> The charge must be calculate .</li>
     * </ul>
     *
     * @param daysRented of movie.
     * @return the amounts of charge.
     * @throws IllegalArgumentException if daysRented is not correct
     */
    double getCharge(int daysRented) {
        if (daysRented <= 0) {
            throw new IllegalArgumentException("daysRented must not be negativ or 0");
        }
        if (this.quality == VideoQuality.QUALITY_4K) {
            return price.getCharge(daysRented) + 2.0;
        }

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

    /**
     * Gets the frequent renter points  of a rental of one film.
     * <p>
     * precondition :
     * <ul>
     * <li> the days rented must be greater than 0.</li>
     * </ul>
     *
     * @param daysRented number of days a film must be rented
     * @return number of frequent renter points.
     * @throws IllegalArgumentException if daysRented is not greater than 0.
     */
    public int getFrequentRenterPoints(int daysRented) {
        if (daysRented <= 0) {
            throw new IllegalArgumentException("daysRented must not be negativ or 0");
        }

        return price.getFrequentRenterPoints(daysRented);
    }

    private VideoQuality getQualityType(String typeInput) {
        VideoQuality tmp = VideoQuality.QUALITY_4K;
        if (typeInput == "hd" || typeInput == "Hd" ||
                typeInput == "hD" || typeInput == "HD") {
            tmp = VideoQuality.QUALITY_HD;
        }
        return tmp;

    }

}

