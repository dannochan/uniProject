package de.uniba.wiai.dsg.ajp.assignment3;

/**
 * give the  films that can be rented by a Customer with different prices
 */
public class Movie {
    private PictureQuality pictureQuality;

    private Price price;

    private String title;
    public Movie(){

    }

    public Movie(String title, int priceCode) {
        this.title = title;
        this.setPriceCode(priceCode);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        if (title == null && title == "") {
            throw new IllegalArgumentException("name must not be null or empty.");
        }

        this.title = title;
    }

    public PictureQuality getPictureQuality() {
        return pictureQuality;
    }

    public void setPictureQuality(PictureQuality pictureQuality) {
        if(pictureQuality ==null){
            throw new IllegalArgumentException("pictureQuality must be null.");
        }
        this.pictureQuality = pictureQuality;
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
        // pictureQuality
        double result = price.getCharge(daysRented);
            if (pictureQuality == PictureQuality.Resolution_4k) {
                 result+= 2 ;
        }
            return result;
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

}
