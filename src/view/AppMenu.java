package view;

import java.util.*;
import java.util.Scanner;

/**
 * Provides the menu interface for the Toy Store Company application. This class
 * displays the main menu options to the user, processes menu selections, and
 * includes a welcome message. It interacts with the user via console input and
 * output.
 * 
 * @author Justin, Fatema, Manveet
 * @version 1.0
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

}
