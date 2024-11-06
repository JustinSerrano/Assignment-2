package model;

/**
 * Abstract superclass representing a general toy. Serves as the base for
 * specific toy types (Figure, Animal, Puzzle, BoardGame).
 * 
 * @author Justin
 * @version 1
 */
public abstract class Toy {

	protected String sn; // Serial Number: unique identifier for each toy
	protected String name; // Name of the toy
	protected String brand; // Brand of the toy
	protected double price; // Price of the toy
	protected int availableCount; // Available stock count
	protected int ageAppropriate; // Minimum age appropriate for the toy

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
	protected Toy(String sn, String name, String brand, double price, int availableCount, int ageAppropriate) {
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

	// Getters and Setters for each field with Javadoc for clarity
	/**
	 * @return the serial number
	 */
	protected String getSn() {
		return sn;
	}

	/**
	 * @param sn the serial number to set
	 */
	protected void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * @return the name of the toy
	 */
	protected String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the brand of the toy
	 */
	protected String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	protected void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the price of the toy
	 */
	protected double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set; must be non-negative.
	 */
	protected void setPrice(double price) {
		if (price < 0) {
			throw new IllegalArgumentException("Price cannot be negative.");
		}
		this.price = price;
	}

	/**
	 * @return the available stock count
	 */
	protected int getAvailableCount() {
		return availableCount;
	}

	/**
	 * @param availableCount the available stock count to set
	 */
	protected void setAvailableCount(int availableCount) {
		this.availableCount = availableCount;
	}

	/**
	 * @return the minimum appropriate age
	 */
	protected int getAgeAppropriate() {
		return ageAppropriate;
	}

	/**
	 * @param ageAppropriate the minimum appropriate age to set
	 */
	protected void setAgeAppropriate(int ageAppropriate) {
		this.ageAppropriate = ageAppropriate;
	}

	/**
	 * Abstract method for getting the toy type as a string.
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
	 * @return a string representation of the toy's details
	 */
	@Override
	public String toString() {
		return "Toy [Serial Number=" + sn + ", Name=" + name + ", Brand=" + brand + ", Price=" + price
				+ ", Available Count=" + availableCount + ", Age Appropriate=" + ageAppropriate + "]";
	}
}
