package controller;

import model.*;
import view.AppMenu;

import java.io.*;
import java.util.*;

import exceptions.NegativeNumberException;
import exceptions.PlayerCountException;

/**
 * Manages the toy inventory, including loading data from file and displaying
 * the main menu. ToyManager reads the toy data from `toys.txt`, creates Toy
 * objects, and provides a menu interface for user interaction.
 * 
 * @author Justin, Fatema, Manveet
 * @version 2.0
 */
public class ToyManager {

	private static final String FILE_PATH = "res/toys.txt"; // Path to toy data file
	private AppMenu menu = new AppMenu(); // Menu for displaying application options
	private Scanner input = new Scanner(System.in);
	private ArrayList<Toy> toys = new ArrayList<>(); // List to store all loaded toy objects

	// TODO: *Optional* Gift suggestion

	/**
	 * Constructor that loads data from the file and launches the application.
	 * Initializes the application by loading toys from `toys.txt` and starting the
	 * main menu.
	 */
	public ToyManager() {
		loadData();
		launchApp(); // Start the main application menu
	}

	/**
	 * Loads toy data from the `toys.txt` file. Each line in the file is parsed to
	 * create a specific Toy object based on the serial number prefix. Logs an error
	 * if parsing fails for any line.
	 */
	private void loadData() {
		File file = new File(FILE_PATH);

		if (!file.exists()) {
			createNewFile(file);
		} else {
			parseFileData(file);
		}
	}

	/**
	 * Creates a new file if it does not exist.
	 *
	 * @param file The file to create.
	 */
	private void createNewFile(File file) {
		try {
			file.createNewFile();
			System.out.println("Created new file: " + FILE_PATH);
		} catch (IOException e) {
			System.out.println("Error creating file: " + e.getMessage());
		}
	}

	/**
	 * Parses existing toy data from file and loads it into the inventory. Each line
	 * in the file is parsed to create a specific Toy object based on the serial
	 * number prefix. If an error occurs in parsing, it is logged for review.
	 *
	 * @param file The file containing toy data.
	 */
	private void parseFileData(File file) {
		try (Scanner inputFile = new Scanner(file)) {
			// Process each line of the file
			while (inputFile.hasNext()) {
				String curLine = inputFile.nextLine();
				String[] splittedLine = curLine.split(";"); // Split line by semicolon to get attributes
				String serialNumber = splittedLine[0];
				char typeIndicator = serialNumber.charAt(0); // Determine toy type based on first digit

				try {
					Toy toy = null;
					switch (typeIndicator) {
					case '0':
					case '1': // Figures
						toy = new Figures(serialNumber, splittedLine[1], splittedLine[2],
								Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]),
								Integer.parseInt(splittedLine[5]), splittedLine[6].charAt(0));
						break;
					case '2':
					case '3': // Animals
						toy = new Animals(serialNumber, splittedLine[1], splittedLine[2],
								Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]),
								Integer.parseInt(splittedLine[5]), splittedLine[6], splittedLine[7].charAt(0));
						break;
					case '4':
					case '5':
					case '6': // Puzzles
						toy = new Puzzles(serialNumber, splittedLine[1], splittedLine[2],
								Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]),
								Integer.parseInt(splittedLine[5]), splittedLine[6].charAt(0));
						break;
					case '7':
					case '8':
					case '9': // BoardGames
						String[] playerRange = splittedLine[6].split("-");
						int minPlayers = Integer.parseInt(playerRange[0]);
						int maxPlayers = Integer.parseInt(playerRange[1]);

						// Pass `splittedLine[7]` directly as comma-separated designer names
						toy = new BoardGames(serialNumber, splittedLine[1], splittedLine[2],
								Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]),
								Integer.parseInt(splittedLine[5]), minPlayers, maxPlayers, splittedLine[7]);
						break;
					default:
						System.out.println("Unknown toy type: " + curLine);
					}

					if (toy != null) {
						toys.add(toy); // Add created toy to list
					}
				} catch (Exception e) {
					System.out.println("Error parsing line: " + curLine + " - " + e.getMessage());
				}
			}

		} catch (IOException e) {
			System.out.println("Error accessing file: " + e.getMessage());
		}
	}

	/**
	 * Launches the main application menu, allowing users to interact with the toy
	 * inventory. Options include searching for toys, adding or removing toys, and
	 * exiting the application.
	 */
	private void launchApp() {
		menu.printWelcomeMessage();
		int select;
		do {
			select = menu.showMainMenu(); // Guaranteed to be valid (1–4)

			switch (select) {
			case 1: // Searching and purchasing toy
				handleSearch(); // Call handleSearch
				break;
			case 2: // Adding a new toy
				addToy();
				break;
			case 3: // Removing a toy)
				removeToy();
				break;
			case 4: // Save the updated list back to toys.txt
				updateData();
				System.out.println("\nSaving Data Into Database...\n");
				menu.printExitMessage();
				break;
			}
		} while (select != 4); // Loop until user chooses to exit
	}

	/**
	 * Handles the search functionality based on the user's choice in the search
	 * menu. The user is returned to the main menu only if they explicitly select
	 * "Back to Main Menu".
	 */
	private void handleSearch() {
		while (true) {
			int select = menu.showSearchMenu(); // Show search menu each time

			List<String> formattedResults;
			int choice;

			switch (select) {
			case 1: // Search by Serial Number
				String serialNumber = getValidatedSerialNumber();
				formattedResults = searchBySerialNumber(serialNumber);
				// Only display results if matches are found
				if (!formattedResults.get(0).equals("No matching toys found.")) {
					choice = menu.displaySearchResults(formattedResults);
					processSearchChoice(choice, formattedResults.size());
				}
				break;
			case 2: // Search by Toy Name
				System.out.print("\nEnter Toy Name: ");
				String toyName = input.nextLine();
				formattedResults = searchByName(toyName);
				// Only display results if matches are found
				if (!formattedResults.get(0).equals("No matching toys found.")) {
					choice = menu.displaySearchResults(formattedResults);
					processSearchChoice(choice, formattedResults.size());
				}
				break;
			case 3: // Search by Type
				System.out.print("\nEnter Type: ");
				String toyType = input.nextLine();
				formattedResults = searchByType(toyType);
				// Only display results if matches are found
				if (!formattedResults.get(0).equals("No matching toys found.")) {
					choice = menu.displaySearchResults(formattedResults);
					processSearchChoice(choice, formattedResults.size());
				}
				break;
			case 4:
				System.out.println("\nReturning to Main Menu...");
				return;
			default:
				System.out.println("\nInvalid option. Please try again.");
			}
		}
	}

	/**
	 * Searches for toys by serial number.
	 *
	 * @param serialNumber The serial number to search for.
	 * @return A formatted list of search results, including an option to return to
	 *         the search menu.
	 */
	private List<String> searchBySerialNumber(String serialNumber) {
		List<Toy> results = new ArrayList<>();
		for (Toy toy : toys) {
			if (toy.getSn().equals(serialNumber)) { // Match serial number
				results.add(toy);
			}
		}

		// Check if results are empty and notify the user if no match was found
		if (results.isEmpty()) {
			System.out.println("No toy found with the serial number: " + serialNumber);
			return Collections.singletonList("No matching toys found.");
		}
		return menu.formatSearchResults(results);
	}

	/**
	 * Searches for toys by name.
	 *
	 * @param toyName The name of the toy to search for.
	 * @return A formatted list of search results, including an option to return to
	 *         the search menu.
	 */
	private List<String> searchByName(String toyName) {
		List<Toy> results = new ArrayList<>();
		for (Toy toy : toys) {
			// Use contains to allow partial matches,
			// and ignore case by converting both sides to lowercase
			if (toy.getName().toLowerCase().contains(toyName.toLowerCase())) {
				results.add(toy);
			}
		}

		// Check if results are empty and notify the user if no match was found
		if (results.isEmpty()) {
			System.out.println("No toy found with the name: " + toyName);
			return Collections.singletonList("No matching toys found.");
		}
		return menu.formatSearchResults(results);
	}

	/**
	 * Searches for toys by type.
	 *
	 * @param type The type of the toy to search for.
	 * @return A formatted list of search results, including an option to return to
	 *         the search menu.
	 */
	private List<String> searchByType(String type) {
		List<Toy> results = new ArrayList<>();
		for (Toy toy : toys) {
			if (toy.getToyType().equalsIgnoreCase(type)) { // Case-insensitive type match
				results.add(toy);
			}
		}
		// Check if results are empty and notify the user if no match was found
		if (results.isEmpty()) {
			System.out.println("No toy found with the type: " + type);
			return Collections.singletonList("No matching toys found.");
		}
		return menu.formatSearchResults(results);
	}

	/**
	 * Processes the user’s choice from the search results display. If the user
	 * selects a toy, it is removed from the inventory. If they choose the "Back to
	 * Search Menu" option, they are returned to the search menu.
	 *
	 * @param choice      The user's choice as an integer.
	 * @param resultsSize The size of the formatted results list.
	 */
	private void processSearchChoice(int choice, int resultsSize) {
		if (choice == resultsSize) {
			// If choice is the last entry, return to the search menu
			System.out.println("Returning to Search Menu...");
		} else if (choice > 0 && choice < resultsSize) {
			// Remove the selected toy from the list
			toys.remove(toys.get(choice - 1));

			System.out.println("\nThe Transaction Successfully Terminated!");

			// Wait for user to press Enter before continuing
			menu.waitForEnterKey();
		} else {
			System.out.println("Invalid selection. Returning to Search Menu.");
		}
	}

	/**
	 * Handles the addition of a new toy to the inventory. Prompts the user for toy
	 * details, starting with validating the serial number. Each validation check
	 * includes user feedback, and the user is re-prompted until valid input is
	 * received.
	 */
	private void addToy() {
		// Get a validated serial number format
		String sn;
		while (true) {
			sn = getValidatedSerialNumber();

			// Check if the serial number is unique within the inventory
			if (isSerialNumberUnique(sn)) {
				break; // Serial number is unique, so exit the loop
			} else {
				System.out.println("\nA Toy With This Serial Number Already Exists! Try again.");
			}
		}

		// Proceed with other toy details if serial number is valid
		System.out.print("\nEnter Toy Name: ");
		String name = input.nextLine().trim();
		System.out.print("\nEnter Toy Brand: ");
		String brand = input.nextLine().trim();

		// Get a validated price
		double price = getValidatedPrice();
		// Get a validated available counts
		System.out.print("\nEnter Available Counts: ");
		int availableCounts = getValidatedNonNegativeInt();
		// Get a validated appropriate age
		System.out.print("\nEnter Appropriate Age: ");
		int appropriateAge = getValidatedNonNegativeInt();

		// Determine toy type based on the first digit of the serial number
		char firstDigit = sn.charAt(0);
		Toy newToy = null;

		if (firstDigit == '0' || firstDigit == '1') {
			// Adding a Figure
			newToy = getValidatedFigureToy(sn, name, brand, price, availableCounts, appropriateAge);
		} else if (firstDigit == '2' || firstDigit == '3') {
			// Adding an Animal
			newToy = getValidatedAnimalToy(sn, name, brand, price, availableCounts, appropriateAge);
		} else if (firstDigit == '4' || firstDigit == '5' || firstDigit == '6') {
			// Adding a Puzzle
			newToy = getValidatedPuzzleToy(sn, name, brand, price, availableCounts, appropriateAge);
		} else if (firstDigit == '7' || firstDigit == '8' || firstDigit == '9') {
			// Adding a Board Game
			newToy = getValidatedBoardGameToy(sn, name, brand, price, availableCounts, appropriateAge);
		} else {
			System.out.println("\nInvalid serial number prefix. Unable to determine toy type.");
			return; // Exit if the serial number prefix is invalid
		}

		// Add the new toy to the inventory and save to file if the toy was created
		// successfully
		if (newToy != null) {
			toys.add(newToy);
			System.out.println("\nNew Toy Added!");
		}

		// Wait for the user to press Enter before returning to the main menu
		menu.waitForEnterKey();
	}

	/**
	 * Validates the format of a serial number. Ensures the serial number contains
	 * only digits and is exactly 10 digits long.
	 *
	 * @return A valid serial number as a String.
	 */
	private String getValidatedSerialNumber() {
		String sn;
		while (true) {
			System.out.print("\nEnter Serial Number: ");
			sn = input.nextLine().trim();

			if (!sn.matches("\\d+")) {
				System.out.println("\nThe Serial Number Should Only Contain Digits! Try again.");
			} else if (sn.length() != 10) {
				System.out.println("\nThe Serial Number's Length MUST Be 10 Digits! Try again.");
			} else {
				break; // Serial number format is valid
			}
		}
		return sn;
	}

	/**
	 * Checks if a serial number is unique in the inventory.
	 *
	 * @param sn The serial number to check.
	 * @return true if the serial number is unique, false otherwise.
	 */
	private boolean isSerialNumberUnique(String sn) {
		for (Toy toy : toys) {
			if (toy.getSn().equals(sn)) {
				return false; // Serial number is not unique
			}
		}
		return true; // Serial number is unique
	}

	/**
	 * Prompts the user to enter a valid price. Ensures that the price is
	 * non-negative, and handles any invalid inputs or negative values with a custom
	 * exception.
	 *
	 * @return A validated price as a double.
	 */
	private double getValidatedPrice() {
		double price;

		while (true) {
			try {
				System.out.print("\nEnter Price: ");
				price = input.nextDouble();

				// Consume the leftover newline after reading the double
				input.nextLine();

				// Throw an exception if the price is negative
				if (price < 0) {
					throw new NegativeNumberException("\nPrice Cannot be Negative! Try again.");
				}

				// Break if the price is valid
				return price;

			} catch (NegativeNumberException e) {
				System.out.println(e.getMessage()); // Display custom error message
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Input. Please Enter a Numerical Value for the Price.");
				input.nextLine(); // Clear the invalid input from the scanner
			}
		}
	}

	/**
	 * Helper method to validate non-negative integer input. This method prompts the
	 * user for input, ensures it is non-negative, and re-prompts if the input is
	 * invalid.
	 *
	 * @return A validated non-negative integer.
	 */
	private int getValidatedNonNegativeInt() {
		int value;
		while (true) {
			try {
				value = input.nextInt();
				if (value >= 0) {
					return value; // Return the valid, non-negative integer
				} else {
					throw new NegativeNumberException("The number must be non-negative. Try again.");
				}
			} catch (NegativeNumberException e) {
				System.out.println(e.getMessage()); // Display the custom error message
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid input. Please enter a non-negative integer.");
				input.nextLine(); // Clear invalid input from scanner
			}
		}
	}

	/**
	 * Prompts the user to enter and validate all fields required to create a Figure
	 * toy. Ensures that the classification is one of 'A', 'D', or 'H'
	 * (case-insensitive).
	 *
	 * @param sn              The serial number of the toy.
	 * @param name            The name of the toy.
	 * @param brand           The brand of the toy.
	 * @param price           The price of the toy.
	 * @param availableCounts The available stock count for the toy.
	 * @param appropriateAge  The minimum appropriate age for the toy.
	 * @return A newly created Figure toy with validated fields.
	 */
	private Toy getValidatedFigureToy(String sn, String name, String brand, double price, int availableCounts,
			int appropriateAge) {
		char classification;

		// Loop to continuously prompt for a valid classification
		while (true) {
			System.out.print("\nEnter Classification (A for Action, D for Doll, H for Historic): ");
			classification = Character.toUpperCase(input.next().charAt(0));

			// Validate that the classification is one of 'A', 'D', or 'H'
			if (classification == 'A' || classification == 'D' || classification == 'H') {
				break; // Exit loop if classification is valid
			} else {
				System.out.println("\nInvalid Classification. Please Enter 'A', 'D', or 'H'.");
			}
		}

		// Create and return the validated Figure toy
		return new Figures(sn, name, brand, price, availableCounts, appropriateAge, classification);
	}

	/**
	 * Prompts the user to enter and validate all fields required to create an
	 * Animal toy. Ensures that the material is not empty and the size is one of
	 * 'S', 'M', or 'L' (case-insensitive).
	 *
	 * @param sn              The serial number of the toy.
	 * @param name            The name of the toy.
	 * @param brand           The brand of the toy.
	 * @param price           The price of the toy.
	 * @param availableCounts The available stock count for the toy.
	 * @param appropriateAge  The minimum appropriate age for the toy.
	 * @return A newly created Animal toy with validated fields.
	 */
	private Toy getValidatedAnimalToy(String sn, String name, String brand, double price, int availableCounts,
			int appropriateAge) {
		// Validate material input
		String material;
		while (true) {
			System.out.print("\nEnter Material: ");
			material = input.nextLine().trim();

			if (!material.isEmpty()) {
				break; // Exit loop if material is valid
			} else {
				System.out.println("Material cannot be empty. Please try again.");
			}
		}

		// Validate size input
		char size;
		while (true) {
			System.out.print("\nEnter Size (S for Small, M for Medium, L for Large): ");
			size = Character.toUpperCase(input.next().charAt(0));

			if (size == 'S' || size == 'M' || size == 'L') {
				break; // Exit loop if size is valid
			} else {
				System.out.println("Invalid size. Please enter 'S', 'M', or 'L'.");
			}
		}

		// Create and return the validated Animal toy
		return new Animals(sn, name, brand, price, availableCounts, appropriateAge, material, size);
	}

	/**
	 * Prompts the user to enter and validate all fields required to create a Puzzle
	 * toy. Ensures that the puzzle type is one of 'M', 'C', 'L', 'T', or 'R'
	 * (case-insensitive).
	 *
	 * @param sn              The serial number of the toy.
	 * @param name            The name of the toy.
	 * @param brand           The brand of the toy.
	 * @param price           The price of the toy.
	 * @param availableCounts The available stock count for the toy.
	 * @param appropriateAge  The minimum appropriate age for the toy.
	 * @return A newly created Puzzle toy with validated fields.
	 */
	private Toy getValidatedPuzzleToy(String sn, String name, String brand, double price, int availableCounts,
			int appropriateAge) {
		char puzzleType;

		// Loop to validate puzzle type input
		while (true) {
			System.out.print(
					"\nEnter Puzzle Type (M for Mechanical, C for Cryptic, L for Logic, T for Trivia, R for Riddle): ");
			puzzleType = Character.toUpperCase(input.next().charAt(0));

			// Check if puzzle type is valid
			if (puzzleType == 'M' || puzzleType == 'C' || puzzleType == 'L' || puzzleType == 'T' || puzzleType == 'R') {
				break; // Exit loop if valid
			} else {
				System.out.println("Invalid puzzle type. Please enter 'M', 'C', 'L', 'T', or 'R'.");
			}
		}

		// Create and return the validated Puzzle toy
		return new Puzzles(sn, name, brand, price, availableCounts, appropriateAge, puzzleType);
	}

	/**
	 * Prompts the user to enter and validate all fields required to create a Board
	 * Game toy. Ensures that the minimum number of players does not exceed the
	 * maximum and that designer names are input correctly.
	 *
	 * @param sn              The serial number of the toy.
	 * @param name            The name of the toy.
	 * @param brand           The brand of the toy.
	 * @param price           The price of the toy.
	 * @param availableCounts The available stock count for the toy.
	 * @param appropriateAge  The minimum appropriate age for the toy.
	 * @return A newly created BoardGame toy with validated fields.
	 */
	private Toy getValidatedBoardGameToy(String sn, String name, String brand, double price, int availableCounts,
			int appropriateAge) {
		int minPlayers;
		int maxPlayers;

		// Validate minimum and maximum number of players
		while (true) {
			try {
				System.out.print("\nEnter Minimum Number of Players: ");
				minPlayers = getValidatedNonNegativeInt();
				
				System.out.println();

				System.out.print("Enter Maximum Number of Players: ");
				maxPlayers = getValidatedNonNegativeInt();

				// Check if minPlayers <= maxPlayers and both are positive
				if (minPlayers > 0 && maxPlayers >= minPlayers) {
					break; // Exit loop if player counts are valid
				} else {
					// Throw custom exception with an appropriate message
					throw new PlayerCountException(
							"\nInvalid player count. Minimum players cannot exceed maximum, and both must be positive.");
				}
			} catch (PlayerCountException e) {
				System.out.println(e.getMessage()); // Display custom error message
			}
		}

		input.nextLine(); // Clear the buffer after numeric input

		// Validate designer names input
		String designers;
		while (true) {
			System.out.print("\nEnter Designer Names (separate names with commas if more than one): ");
			designers = input.nextLine().trim();

			if (!designers.isEmpty()) {
				break; // Exit loop if designers input is non-empty
			} else {
				System.out.println("\nDesigner names cannot be empty. Please try again.");
			}
		}

		// Create and return the validated BoardGame toy
		return new BoardGames(sn, name, brand, price, availableCounts, appropriateAge, minPlayers, maxPlayers,
				designers);
	}

	/**
	 * Handles the removal of a toy from the inventory by serial number. Prompts the
	 * user for the serial number, displays the toy if found, and asks for
	 * confirmation to remove it.
	 */
	private void removeToy() {
		String sn = getValidatedSerialNumber(); // Read a valid serial number input

		// Search for the toy with the specified serial number
		Toy toyToRemove = null;
		for (Toy toy : toys) {
			if (toy.getSn().equals(sn)) {
				toyToRemove = toy;
				break;
			}
		}

		// If the toy was found, proceed with confirmation
		if (toyToRemove != null) {
			System.out.println("\nThis Item Found:");
			System.out.println("\n\t" + toyToRemove); // Display toy details

			System.out.print("\nDo you want to remove it? (Y/N): ");
			char choice = Character.toUpperCase(input.nextLine().charAt(0)); // Read choice and convert to uppercase

			if (choice == 'Y') {
				toys.remove(toyToRemove); // Remove the toy from the list
				System.out.println("\nItem Removed!");
			} else {
				System.out.println("\nReturning to Main Menu...");
			}
		} else {
			System.out.println("\nNo Item Found with the serial number " + sn + ".");
		}

		// Wait for user to press Enter before returning to the main menu
		menu.waitForEnterKey();
	}

	/**
	 * Updates the current list of toys to the `toys.txt` file. This method iterates
	 * over the `toys` list and writes each toy's data to the file.
	 */
	private void updateData() {
		try (PrintWriter writer = new PrintWriter(new FileWriter("res/toys.txt"))) {
			for (Toy toy : toys) {
				writer.println(toy.toDataString()); // Convert each toy to a formatted string for file storage
			}
		} catch (IOException e) {
			System.out.println("\nError updating toys to file: " + e.getMessage());
		}
	}
}
