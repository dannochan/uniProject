package de.uniba.wiai.dsg.ajp.assignment3;

import java.util.LinkedList;
import java.util.List;
/**
 * Give the information about a Customer who rents a Movie
 */

public class Customer {

	private String name;

	private List<Rental> rentals = new LinkedList<Rental>();

	public Customer(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null && name == ""){
			throw new IllegalArgumentException("name must not be null or empty.");
		}

		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		if (rentals == null && rentals.size() == 0) {
			throw new IllegalArgumentException("rentals must not be empty");
		}
		this.rentals = rentals;
	}
	/**
	 * Statement gives  the information on a Customer  rental record in html format .
	 *
	 * <p>
	 * Precondition:
	 * <ul>
	 * <li> The Statement must not take any input  </li>
	 * </ul>
	 * </p>
	 *
	 *
	 * <p>
	 * Postcondition:
	 * <ul>
	 * <li> All the values to add to the result must be of type String  </li>
	 * </ul>
	 *
	 * @return rental Record of a Customer.
	 */

	// String.valueOf werden löscht.
	public String statement() {
		String result = "Rental Record for " + getName() + "\n";

		int frequentRenterPoints = 0;
		for (Rental each : this.rentals) {
			frequentRenterPoints += each.getFrequentRenterPoints();

			// show figures for this rental
			result += "\t" + each.getMovie().getTitle() + "\t"
					+ each.getCharge() + "\n";
		}

		// add footer lines
		result += "Amount owed is " + getTotalCharge() + "\n";
		result += "You earned " + frequentRenterPoints
				+ " frequent renter points";
		return result;
	}

	/**
	 * Html statement gives  the information on a customer  rental record in html format .
	 *
	 * <p>
	 * Precondition:
	 * <ul>
	 * <li> The Statement must not take any input  </li>
	 * </ul>
	 * </p>
	 *
	 *
	 * <p>
	 * Postcondition:
	 * <ul>
	 * <li> All the values to add to the result must be of type string  </li>
	 * </ul>
	 *
	 * @return rental Record of a customer.
	 */

	public String htmlStatement() {
		String result = "<H1>Rentals for <EM>" + getName() + "</EM></H1><P>\n";

		for (Rental each : rentals) {
			// show figures for each rental
			result += each.getMovie().getTitle() + ": "
					+ each.getCharge() + "<BR>\n";
		}

		// add footer lines
		result += "<P>You owe <EM>" + getTotalCharge()
				+ "</EM><P>\n";
		result += "On this rental you earned <EM>"
				+ getTotalFrequentRenterPoints()
				+ "</EM> frequent renter points<P>";
		return result;
	}

	/**
	 * Get the total charge of all rental of a customer
	 *
	 * <p>
	 * Precondition:
	 * <ul>
	 * <li> The Result muss not be negative . </li>
	 * </ul>
	 * </p>
	 *
	 *
	 * <p>
	 * Postcondition:
	 * <ul>
	 * <li> The charge of each rental muss be to add at result. </li>
	 * </ul>
	 * </p>
	 *
	 * @return The total charge of all rental of a customer
	 *
	 */
	double getTotalCharge() {
		double result = 0;

		for (Rental each : rentals) {
			result += each.getCharge();
		}

		return result;
	}
	/**
	 * Get total frequent renters point of all rentals of a customer.
	 *
	 * <p>
	 * Precondition:
	 * <ul>
	 * <li> The result muss not be negative . </li>
	 * </ul>
	 * </p>
	 *
	 *
	 * <p>
	 * Postcondition:
	 * <ul>
	 * <li> The frequent renter points of each rental muss to be add at result. </li>
	 * </ul>
	 * </p>
	 *
	 * @return The total number of frequent renters points.
	 */

	int getTotalFrequentRenterPoints() {
		int result = 0;

		for (Rental each : rentals) {
			result += each.getFrequentRenterPoints();
		}

		return result;
	}

}
//