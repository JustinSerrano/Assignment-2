/**
 * 
 */
package view;

import java.util.Scanner;

/**
 * 
 * @author Justin
 * @version 1
 */
public class AppMenu {

	// Field
	private Scanner input = new Scanner(System.in); // Scanner for reading user input

	/**
	 * Prints a formatted welcome message for the toy store company application.
	 * 
	 */
	public void printWelcomeMessage() {
		String formattedMessage = String.format("*     %s     *", "WELCOME TO TOY STORE COMPANY!");
		int length = formattedMessage.length();

		// Print the top border
		System.out.println("*".repeat(length)); // Dynamically create a border of the same length
		System.out.println(formattedMessage); // Print the formatted welcome message
		// Print the bottom border
		System.out.println("*".repeat(length)); // Dynamically create a border of the same length
	}

	/**
	 * Displays the main menu options to the user and retrieves their selection.
	 * 
	 * @return The integer value representing the user's menu choice (1, 2, 3, or
	 *         4).
	 */
	public int showMainMenu() {
		System.out.println("\nHow We May Help You?\n");
		System.out.println("\t(1) Search Inventory and Purchase Toy");
		System.out.println("\t(2) Add New Toy");
		System.out.println("\t(3) Remove Toy");
		System.out.println("\t(4) Save & Exit");
		System.out.print("\nEnter option: ");

		int select = input.nextInt(); // Read user input

		// Validate input; if empty, prompt again
		if (select < 1 || select > 4) {
			System.out.println("Invalid input. Please enter a valid option.");
			return showMainMenu(); // Recursively prompt again
		}

		return select; // Return the selection
	}

}
