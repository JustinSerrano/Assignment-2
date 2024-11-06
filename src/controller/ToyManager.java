package controller;

import model.*;
import view.AppMenu;

import java.io.*;
import java.util.*;

/**
 * Manages the toy inventory, including loading data from file and displaying
 * the main menu. ToyManager reads the toy data from `toys.txt`, creates Toy
 * objects, and provides a menu interface.
 * 
 * @author Justin, Fatema, Manveet
 * @version 1.0
 */
public class ToyManager {

	private static final String FILE_PATH = "res/toys.txt"; // Path to toy data file
	private AppMenu menu = new AppMenu(); // Menu for displaying application options
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

			// Check if the file exists, create a new one if not
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
			case 1:
				// TODO: Implement functionality for option 1 (e.g., search inventory)
				break;
			case 2:
				// TODO: Implement functionality for option 2 (e.g., add new toy)
				break;
			case 3:
				// TODO: Implement functionality for option 3 (e.g., remove toy)
				break;
			case 4:
				System.out.println("Exiting application...");
				break;
			}
		} while (select != 4); // Loop until user chooses to exit
	}
}
