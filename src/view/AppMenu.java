package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Provides the menu interface for the Toy Store Company application. This class
 * displays the main menu and search menu options, processes menu selections,
 * and includes a welcome message. It interacts with the user via console input
 * and output.
 * 
 * @version 1.0 author Justin, Fatema, Manveet
 */
public class AppMenu {

	// Field
	private Scanner input = new Scanner(System.in); // Scanner for reading user input

	/**
	 * Prints a formatted welcome message for the Toy Store Company application. The
	 * message is enclosed within a border of asterisks for emphasis.
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
	 * Options include searching the inventory, adding a new toy, removing a toy,
	 * and exiting the application.
	 *
	 * @return The integer value representing the user's menu choice (1, 2, 3, or
	 *         4).
	 */
	public int showMainMenu() {
		int select = 0; // Default value for menu selection
		boolean validInput = false; // Track if input is valid

		do {
			System.out.println("\nHow We May Help You?\n");
			System.out.println("\t(1) Search Inventory and Purchase Toy");
			System.out.println("\t(2) Add New Toy");
			System.out.println("\t(3) Remove Toy");
			System.out.println("\t(4) Save & Exit");
			System.out.print("\nEnter option: ");

			try {
				select = input.nextInt(); // Attempt to read an integer from user

				// Check if the input is within range
				if (select >= 1 && select <= 4) {
					validInput = true; // Set to true if input is valid
				} else {
					System.out.println("Invalid input. Please enter a number between 1 and 4.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number between 1 and 4.");
				input.nextLine(); // Clear invalid input
			}
		} while (!validInput); // Repeat until valid input is received

		return select; // Return the validated selection
	}

	/**
	 * Displays the search menu options to the user and retrieves their selection.
	 * The options include searching by serial number, toy name, or type, as well as
	 * returning to the main menu.
	 *
	 * @return The integer value representing the user's menu choice (1, 2, 3, or
	 *         4).
	 */
	public int showSearchMenu() {
		int select = 0; // Default value for menu selection
		boolean validInput = false; // Track if input is valid

		do {
			System.out.println("\nFind Toys With:\n");
			System.out.println("(1) Serial Number (SN)");
			System.out.println("(2) Toy Name");
			System.out.println("(3) Type");
			System.out.println("(4) Back to Main Menu");
			System.out.print("\nEnter Option: ");

			try {
				select = input.nextInt(); // Attempt to read an integer from user

				// Check if the input is within range
				if (select >= 1 && select <= 4) {
					validInput = true; // Set to true if input is valid
				} else {
					System.out.println("Invalid input. Please enter a number between 1 and 4.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number between 1 and 4.");
				input.nextLine(); // Clear invalid input
			}
		} while (!validInput); // Repeat until valid input is received

		return select; // Return the validated selection
	}

	/**
	 * Displays search results and allows the user to select an option. The results
	 * are displayed with an ascending order number, with the last entry as a "Back
	 * to Search Menu" option.
	 *
	 * @param results A list of formatted search results, with the last entry as
	 *                "Back to Search Menu".
	 * @return The user’s choice as an integer representing their selection.
	 */
	public int displaySearchResults(List<String> results) {
		System.out.println("\nHere are the search results:\n");

		// Print each formatted result line by line
		for (String result : results) {
			System.out.println(result);
		}

		// Prompt for user choice
		System.out.print("\nEnter option: ");
		int choice;
		try {
			choice = input.nextInt(); // Read user choice
		} catch (InputMismatchException e) {
			input.nextLine(); // Clear invalid input
			choice = -1; // Set to an invalid number to trigger re-prompt
		}

		return choice;
	}
}
