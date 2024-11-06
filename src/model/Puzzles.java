package model;

/**
 * Represents a Puzzle toy with a specific puzzle type.
 * 
 * @author Justin, Fatema, Manveet
 * @version 1.0
 */
public class Puzzles extends Toy {

	/**
	 * 'M' for Mechanical, 'C' for Cryptic, 'L' for Logic, 'T' for Trivia, 'R' for
	 * Riddle
	 */
	private char puzzleType;

	/**
	 * Constructs a Puzzle with the specified attributes.
	 *
	 * @param sn             Unique serial number; must start with 4, 5, or 6 and be
	 *                       10 digits.
	 * @param name           Name of the puzzle.
	 * @param brand          Brand of the puzzle.
	 * @param price          Price of the puzzle; must be non-negative.
	 * @param availableCount Stock count for the puzzle.
	 * @param ageAppropriate Minimum age appropriate for the puzzle.
	 * @param puzzleType     Type of puzzle ('M' for Mechanical, 'C' for Cryptic,
	 *                       'L' for Logic, 'T' for Trivia, 'R' for Riddle).
	 * @throws IllegalArgumentException if serial number or puzzle type is invalid.
	 */
	public Puzzles(String sn, String name, String brand, double price, int availableCount, int ageAppropriate,
			char puzzleType) {
		super(sn, name, brand, price, availableCount, ageAppropriate);

		// Validate that the serial number starts with 4, 5, or 6 and is 10 digits
		if (!sn.matches("^[456]\\d{9}$")) {
			throw new IllegalArgumentException("Serial number must start with 4, 5, or 6 and be 10 digits long.");
		}

		// Validate that puzzle type is 'M', 'C', 'L', 'T', or 'R'
		if (puzzleType != 'M' && puzzleType != 'C' && puzzleType != 'L' && puzzleType != 'T' && puzzleType != 'R') {
			throw new IllegalArgumentException(
					"Puzzle type must be 'M' (Mechanical), 'C' (Cryptic), 'L' (Logic), 'T' (Trivia), or 'R' (Riddle).");
		}

		this.puzzleType = puzzleType;
	}

	/**
	 * @return the puzzle type
	 */
	public char getPuzzleType() {
		return puzzleType;
	}

	/**
	 * Sets the puzzle type of the puzzle toy.
	 *
	 * @param puzzleType the puzzle type to set; must be 'M', 'C', 'L', 'T', or 'R'
	 * @throws IllegalArgumentException if puzzle type is invalid
	 */
	public void setPuzzleType(char puzzleType) {
		if (puzzleType != 'M' && puzzleType != 'C' && puzzleType != 'L' && puzzleType != 'T' && puzzleType != 'R') {
			throw new IllegalArgumentException(
					"Puzzle type must be 'M' (Mechanical), 'C' (Cryptic), 'L' (Logic), 'T' (Trivia), or 'R' (Riddle).");
		}
		this.puzzleType = puzzleType;
	}

	/**
	 * Returns the toy type as "Puzzle".
	 *
	 * @return the toy type
	 */
	@Override
	public String getToyType() {
		return "Puzzle";
	}

	/**
	 * Returns a formatted string with details specific to the Puzzle toy.
	 *
	 * @return a string representation of the puzzle's details
	 */
	@Override
	public String toString() {
		return super.toString() + ", PuzzleType=" + puzzleType;
	}

	/**
	 * Returns a string formatted for saving to `toys.txt`.
	 *
	 * @return a string representation of the toy's data for file storage
	 */
	@Override
	public String toDataString() {
		return String.join(";", sn, name, brand, String.valueOf(price), String.valueOf(availableCount),
				String.valueOf(ageAppropriate), String.valueOf(puzzleType));
	}
}
