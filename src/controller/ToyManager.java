package controller;

import model.*;
import view.AppMenu;

import java.io.*;
import java.util.*;

/**
 * Manages the toy inventory, including loading data from file and displaying
 * the main menu. ToyManager reads the toy data from `toys.txt`, creates Toy
 * objects, and provides a menu interface for user interaction.
 * 
 * @version 1.0 author Justin, Fatema, Manveet
 */
public class ToyManager {

	private static final String FILE_PATH = "res/toys.txt"; // Path to toy data file
	private AppMenu menu = new AppMenu(); // Menu for displaying application options
	private Scanner input = new Scanner(System.in);
	private ArrayList<Toy> toys = new ArrayList<>(); // List to store all loaded toy objects

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
	 * create a specific Toy object based on the serial number prefix. If an error
	 * occurs in parsing, it is logged for review.
	 */
	private void loadData() {
		try {
			File file = new File(FILE_PATH);

			// Check if the file exists; create a new one if not
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("Created new file: " + FILE_PATH);
			} else {
				Scanner input = new Scanner(file);

				// Process each line of the file
				while (input.hasNext()) {
					String curLine = input.nextLine();
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
				input.close();
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
				handleAdd();
				break;
			case 3:
				// TODO: Implement functionality for option 3 (e.g., remove toy)
				break;
			case 4:
				// Save the updated list back to toys.txt
				updateData();
				System.out.println("Exiting application...");
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
				System.out.print("\nEnter Serial Number (SN): ");
				String serialNumber = input.nextLine();
				formattedResults = searchBySerialNumber(serialNumber);
				choice = menu.displaySearchResults(formattedResults);
				processSearchChoice(choice, formattedResults.size());
				break;
			case 2: // Search by Toy Name
				System.out.print("\nEnter Toy Name: ");
				String toyName = input.nextLine();
				formattedResults = searchByName(toyName);
				choice = menu.displaySearchResults(formattedResults);
				processSearchChoice(choice, formattedResults.size());
				break;
			case 3: // Search by Type
				System.out.print("\nEnter Type: ");
				String toyType = input.nextLine();
				formattedResults = searchByType(toyType);
				choice = menu.displaySearchResults(formattedResults);
				processSearchChoice(choice, formattedResults.size());
				break;
			case 4:
				System.out.println("\nReturning to Main Menu...");
				return;
			default:
				System.out.println("Invalid option. Please try again.");
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
	 * details and determines the toy type based on the serial number prefix. Each
	 * toy type requires specific attributes, which are gathered accordingly.
	 */
	private void handleAdd() {
		System.out.print("\nEnter Serial Number: ");
		String sn = input.nextLine().trim();
		System.out.print("\nEnter Toy Name: ");
		String name = input.nextLine().trim();
		System.out.print("\nEnter Toy Brand: ");
		String brand = input.nextLine().trim();
		System.out.print("\nEnter Price: ");
		double price = input.nextDouble();
		System.out.print("\nEnter Available Counts: ");
		int availableCounts = input.nextInt();
		System.out.print("\nEnter Appropriate Age: ");
		int appropriateAge = input.nextInt();

		// Determine toy type based on the first digit of the serial number
		char firstDigit = sn.charAt(0);
		Toy newToy = null;

		if (firstDigit == '0' || firstDigit == '1') {
			// Adding a Figure
			System.out.print("\nEnter Classification (A for Action, D for Doll, H for Historic): ");
			char classification = Character.toUpperCase(input.next().charAt(0));
			newToy = new Figures(sn, name, brand, price, availableCounts, appropriateAge, classification);
		} else if (firstDigit == '2' || firstDigit == '3') {
			// Adding an Animal
			System.out.print("\nEnter Material: ");
			String material = input.next().trim();
			System.out.print("\nEnter Size (S for Small, M for Medium, L for Large): ");
			char size = Character.toUpperCase(input.next().charAt(0));
			newToy = new Animals(sn, name, brand, price, availableCounts, appropriateAge, material, size);
		} else if (firstDigit == '4' || firstDigit == '5' || firstDigit == '6') {
			// Adding a Puzzle
			System.out.print(
					"\nEnter Puzzle Type (M for Mechanical, C for Cryptic, L for Logic, T for Trivia, R for Riddle): ");
			char puzzleType = Character.toUpperCase(input.next().charAt(0));
			newToy = new Puzzles(sn, name, brand, price, availableCounts, appropriateAge, puzzleType);
		} else if (firstDigit == '7' || firstDigit == '8' || firstDigit == '9') {
			// Adding a Board Game
			System.out.print("\nEnter Minimum Number of Players: ");
			int minPlayers = input.nextInt();
			System.out.print("\nEnter Maximum Number of Players: ");
			int maxPlayers = input.nextInt();
			input.nextLine(); // Clear the buffer
			System.out.print("\nEnter Designer Names (separate names with commas if more than one): ");
			String designers = input.nextLine().trim();
			newToy = new BoardGames(sn, name, brand, price, availableCounts, appropriateAge, minPlayers, maxPlayers,
					designers);
		} else {
			System.out.println("\nInvalid serial number prefix. Unable to determine toy type.");
			return; // Exit if the serial number prefix is invalid
		}

		// Add the new toy to the inventory and save to file if the toy was created
		// successfully
		if (newToy != null) {
			toys.add(newToy);
			updateData(); // Save to toys.txt
			System.out.println("\nNew Toy Added Successfully!");
		}

		// Wait for the user to press Enter before returning to the main menu
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
			System.out.println("Error updating toys to file: " + e.getMessage());
		}
	}
}
