package model;

/**
 * Represents a Figure toy with a specific classification.
 * 
 * @author Justin
 * @version 1
 */
public class Figures extends Toy {

    private char classification; // 'A' for Action, 'D' for Doll, 'H' for Historic

    /**
     * Constructs a Figure with the specified attributes.
     *
     * @param sn             Unique serial number; must start with 0 or 1 and be 10 digits.
     * @param name           Name of the figure.
     * @param brand          Brand of the figure.
     * @param price          Price of the figure; must be non-negative.
     * @param availableCount Stock count for the figure.
     * @param ageAppropriate Minimum age appropriate for the figure.
     * @param classification Classification type ('A' for Action, 'D' for Doll, 'H' for Historic).
     * @throws IllegalArgumentException if serial number or classification is invalid.
     */
    public Figures(String sn, String name, String brand, double price, int availableCount, int ageAppropriate,
                      char classification) {
        super(sn, name, brand, price, availableCount, ageAppropriate);

        // Validate that the serial number starts with 0 or 1 and is 10 digits
        if (!sn.matches("^[01]\\d{9}$")) {
            throw new IllegalArgumentException("Serial number must start with 0 or 1 and be 10 digits long.");
        }

        // Validate that classification is 'A', 'D', or 'H'
        if (classification != 'A' && classification != 'D' && classification != 'H') {
            throw new IllegalArgumentException("Classification must be 'A' (Action), 'D' (Doll), or 'H' (Historic).");
        }

        this.classification = classification;
    }

    /**
     * @return the classification type of the figure
     */
    public char getClassification() {
        return classification;
    }

    /**
     * Sets the classification type for the figure.
     *
     * @param classification the classification to set ('A', 'D', or 'H')
     * @throws IllegalArgumentException if classification is invalid
     */
    public void setClassification(char classification) {
        if (classification != 'A' && classification != 'D' && classification != 'H') {
            throw new IllegalArgumentException("Classification must be 'A' (Action), 'D' (Doll), or 'H' (Historic).");
        }
        this.classification = classification;
    }

    /**
     * Returns the toy type as "Figure".
     *
     * @return the toy type
     */
    @Override
    public String getToyType() {
        return "Figure";
    }

    /**
     * Returns a formatted string with details specific to the Figure toy.
     *
     * @return a string representation of the figure's details
     */
    @Override
    public String toString() {
        return super.toString() + ", Classification=" + classification;
    }

    /**
     * Returns a string formatted for saving to `toys.txt`.
     *
     * @return a string representation of the toy's data for file storage
     */
    @Override
    public String toDataString() {
        return String.join(";", sn, name, brand, String.valueOf(price),
                String.valueOf(availableCount), String.valueOf(ageAppropriate), String.valueOf(classification));
    }
}
