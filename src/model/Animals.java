package model;

/**
 * Represents an Animal toy with a specific material and size.
 * 
 * @author Justin
 * @version 1
 */
public class Animals extends Toy {

    private String material; // Material of the toy (e.g., "Plastic", "Fabric")
    private char size;       // Size of the toy: 'S' for Small, 'M' for Medium, 'L' for Large

    /**
     * Constructs an Animal toy with the specified attributes.
     *
     * @param sn             Unique serial number; must start with 2 or 3 and be 10 digits.
     * @param name           Name of the animal toy.
     * @param brand          Brand of the animal toy.
     * @param price          Price of the animal toy; must be non-negative.
     * @param availableCount Stock count for the animal toy.
     * @param ageAppropriate Minimum age appropriate for the animal toy.
     * @param material       Material of the toy (e.g., "Plastic", "Fabric").
     * @param size           Size of the toy ('S' for Small, 'M' for Medium, 'L' for Large).
     * @throws IllegalArgumentException if serial number or size is invalid.
     */
    public Animals(String sn, String name, String brand, double price, int availableCount, int ageAppropriate,
                      String material, char size) {
        super(sn, name, brand, price, availableCount, ageAppropriate);

        // Validate that the serial number starts with 2 or 3 and is 10 digits
        if (!sn.matches("^[23]\\d{9}$")) {
            throw new IllegalArgumentException("Serial number must start with 2 or 3 and be 10 digits long.");
        }

        // Validate that size is 'S', 'M', or 'L'
        if (size != 'S' && size != 'M' && size != 'L') {
            throw new IllegalArgumentException("Size must be 'S' (Small), 'M' (Medium), or 'L' (Large).");
        }

        this.material = material;
        this.size = size;
    }

    /**
     * @return the material of the animal toy
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Sets the material of the animal toy.
     *
     * @param material the material to set
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * @return the size of the animal toy
     */
    public char getSize() {
        return size;
    }

    /**
     * Sets the size of the animal toy.
     *
     * @param size the size to set; must be 'S', 'M', or 'L'
     * @throws IllegalArgumentException if size is invalid
     */
    public void setSize(char size) {
        if (size != 'S' && size != 'M' && size != 'L') {
            throw new IllegalArgumentException("Size must be 'S' (Small), 'M' (Medium), or 'L' (Large).");
        }
        this.size = size;
    }

    /**
     * Returns the toy type as "Animal".
     *
     * @return the toy type
     */
    @Override
    public String getToyType() {
        return "Animal";
    }

    /**
     * Returns a formatted string with details specific to the Animal toy.
     *
     * @return a string representation of the animal toy's details
     */
    @Override
    public String toString() {
        return super.toString() + ", Material=" + material + ", Size=" + size;
    }

    /**
     * Returns a string formatted for saving to `toys.txt`.
     *
     * @return a string representation of the toy's data for file storage
     */
    @Override
    public String toDataString() {
        return String.join(";", sn, name, brand, String.valueOf(price),
                String.valueOf(availableCount), String.valueOf(ageAppropriate), material, String.valueOf(size));
    }
}
