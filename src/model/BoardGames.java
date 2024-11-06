package model;

import java.util.Arrays;

/**
 * Represents a Board Game toy with a player range and designers.
 * 
 * @author Justin
 * @version 1
 */
public class BoardGames extends Toy {

    private int minPlayers;        // Minimum number of players
    private int maxPlayers;        // Maximum number of players
    private String[] designers;    // Array of designers for the board game

    /**
     * Constructs a Board Game with the specified attributes.
     *
     * @param sn             Unique serial number; must start with 7, 8, or 9 and be 10 digits.
     * @param name           Name of the board game.
     * @param brand          Brand of the board game.
     * @param price          Price of the board game; must be non-negative.
     * @param availableCount Stock count for the board game.
     * @param ageAppropriate Minimum age appropriate for the board game.
     * @param minPlayers     Minimum number of players for the game.
     * @param maxPlayers     Maximum number of players for the game.
     * @param designers      A comma-separated string of designer names.
     * @throws IllegalArgumentException if serial number, player range, or designers are invalid.
     */
    public BoardGames(String sn, String name, String brand, double price, int availableCount, int ageAppropriate,
                      int minPlayers, int maxPlayers, String designers) {
        super(sn, name, brand, price, availableCount, ageAppropriate);

        // Validate that the serial number starts with 7, 8, or 9 and is 10 digits
        if (!sn.matches("^[789]\\d{9}$")) {
            throw new IllegalArgumentException("Serial number must start with 7, 8, or 9 and be 10 digits long.");
        }

        // Validate that minPlayers is less than or equal to maxPlayers
        if (minPlayers > maxPlayers) {
            throw new IllegalArgumentException("Minimum players cannot exceed maximum players.");
        }

        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;

        // Split the designers by comma and trim any leading/trailing whitespace from each name
        this.designers = Arrays.stream(designers.split(","))
                               .map(String::trim)
                               .toArray(String[]::new);
    }

    /**
     * @return the minimum number of players
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * @return the maximum number of players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * @return the array of designers
     */
    public String[] getDesigners() {
        return designers;
    }

    /**
     * Sets the designers for the board game.
     *
     * @param designers array of designer names
     */
    public void setDesigners(String[] designers) {
        this.designers = designers;
    }

    /**
     * Returns the toy type as "BoardGame".
     *
     * @return the toy type
     */
    @Override
    public String getToyType() {
        return "BoardGame";
    }

    /**
     * Returns a formatted string with details specific to the Board Game toy.
     *
     * @return a string representation of the board game's details
     */
    @Override
    public String toString() {
        return super.toString() + ", Players=" + minPlayers + "-" + maxPlayers +
                ", Designers=" + String.join(", ", designers);
    }

    /**
     * Returns a string formatted for saving to `toys.txt`.
     *
     * @return a string representation of the toy's data for file storage
     */
    @Override
    public String toDataString() {
        return String.join(";", sn, name, brand, String.valueOf(price),
                String.valueOf(availableCount), String.valueOf(ageAppropriate),
                minPlayers + "-" + maxPlayers, String.join(", ", designers));
    }
}
