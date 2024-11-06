package model;

/**
 * Abstract superclass representing a general toy. Serves as the base for
 * specific toy types (e.g., Figure, Animal, Puzzle, BoardGame). Contains common
 * attributes and methods shared across different toy types. Each subclass must
 * implement specific behavior for `getToyType` and `toDataString`.
 * 
 * @version 1.0
 * @author Justin, Fatema, Manveet
 */
public abstract class Toy {

	private String sn; // Serial Number: unique identifier for each toy
	private String name; // Name of the toy
	private String brand; // Brand of the toy
	private double price; // Price of the toy
	private int availableCount; // Available stock count
	private int ageAppropriate; // Minimum age appropriate for the toy

	/**
	 * Constructor for creating a Toy with common attributes.
	 *
	 * @param sn             Unique serial number for the toy.
	 * @param name           Name of the toy.
	 * @param brand          Brand name of the toy.
	 * @param price          Price of the toy; must be non-negative.
	 * @param availableCount Stock count for the toy.
	 * @param ageAppropriate Minimum age appropriate for the toy.
	 * @throws IllegalArgumentException if price is negative.
	 */
	public Toy(String sn, String name, String brand, double price, int availableCount, int ageAppropriate) {
		if (price < 0) {
			throw new IllegalArgumentException("Price cannot be negative.");
		}
		this.sn = sn;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.availableCount = availableCount;
		this.ageAppropriate = ageAppropriate;
	}

	/**
	 * Gets the serial number of the toy.
	 * 
	 * @return the serial number of the toy as a String.
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * Sets the serial number of the toy.
	 * 
	 * @param sn the serial number to set. This should be a unique identifier for
	 *           each toy.
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * Gets the name of the toy.
	 * 
	 * @return the name of the toy as a String.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the toy.
	 * 
	 * @param name the name to set for the toy. This should be a non-empty string
	 *             representing the toy's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the brand of the toy.
	 * 
	 * @return the brand of the toy as a String.
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * Sets the brand of the toy.
	 * 
	 * @param brand the brand to set for the toy. This should be a non-empty string
	 *              representing the toy's brand.
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * Gets the price of the toy.
	 * 
	 * @return the price of the toy as a double.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the price of the toy.
	 * 
	 * @param price the price to set; must be non-negative.
	 * @throws IllegalArgumentException if the price is negative.
	 */
	public void setPrice(double price) {
		if (price < 0) {
			throw new IllegalArgumentException("Price cannot be negative.");
		}
		this.price = price;
	}

	/**
	 * Gets the available stock count of the toy.
	 * 
	 * @return the available stock count as an integer.
	 */
	public int getAvailableCount() {
		return availableCount;
	}

	/**
	 * Sets the available stock count of the toy.
	 * 
	 * @param availableCount the available stock count to set. This should be a
	 *                       non-negative integer representing the number of toys in
	 *                       stock.
	 */
	public void setAvailableCount(int availableCount) {
		this.availableCount = availableCount;
	}

	/**
	 * Gets the minimum appropriate age for the toy.
	 * 
	 * @return the minimum age for which the toy is deemed appropriate.
	 */
	public int getAgeAppropriate() {
		return ageAppropriate;
	}

	/**
	 * Sets the minimum appropriate age for the toy.
	 * 
	 * @param ageAppropriate the minimum age appropriate for the toy. This should be
	 *                       a non-negative integer representing the age
	 *                       suitability.
	 */
	public void setAgeAppropriate(int ageAppropriate) {
		this.ageAppropriate = ageAppropriate;
	}

	/**
	 * Abstract method for getting the toy type as a string. Must be implemented by
	 * subclasses to return the specific type of the toy.
	 *
	 * @return the type of the toy (e.g., "Figure", "Animal").
	 */
	public abstract String getToyType();

	/**
	 * Abstract method for saving toy data to a file-compatible format. Each
	 * subclass should implement this method to return a formatted string
	 * representing the toy's data for saving to a file.
	 *
	 * @return a string formatted for saving to `toys.txt`
	 */
	public abstract String toDataString();

	/**
	 * Returns a formatted string representing the toy details.
	 *
	 * @return a string representation of the toy's details, including serial
	 *         number, name, brand, price, available count, and minimum appropriate
	 *         age.
	 */
	@Override
	public String toString() {
		return "Toy [Serial Number=" + sn + ", Name=" + name + ", Brand=" + brand + ", Price=" + price
				+ ", Available Count=" + availableCount + ", Age Appropriate=" + ageAppropriate + "]";
	}
}
