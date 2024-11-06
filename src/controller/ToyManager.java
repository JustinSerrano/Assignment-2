package controller;

import model.*;
import view.AppMenu;

import java.io.*;
import java.util.*;

/**
 * Manages the toy inventory, including loading from file and displaying the
 * main menu.
 * 
 * @author Justin
 * @version 1
 */
public class ToyManager {

	private static final String FILE_PATH = "res/toys.txt";
	private AppMenu menu = new AppMenu();
	private ArrayList<Toy> toys = new ArrayList<>(); // Store all toys

	/**
	 * Constructor that loads data and launches the application.
	 */
	public ToyManager() {
		loadData();
		launchApp(); // Launch the main application menu
	}

	/**
	 * Loads toy data from the `toys.txt` file.
	 */
	private void loadData() {
		try {
			File file = new File(FILE_PATH);
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("Created new file: " + FILE_PATH);
			} else {
				Scanner input = new Scanner(file);

				while (input.hasNext()) {
					String curLine = input.nextLine();
					String[] splittedLine = curLine.split(";");

					// Check the first digit of the serial number to determine toy type
					String serialNumber = splittedLine[0];
					char typeIndicator = serialNumber.charAt(0);

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
							toy = new BoardGames(serialNumber, splittedLine[1], splittedLine[2],
									Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]),
									Integer.parseInt(splittedLine[5]), minPlayers, maxPlayers, splittedLine[7]);
							break;
						default:
							System.out.println("Unknown toy type: " + curLine);
						}

						if (toy != null) {
							toys.add(toy);
						}
					} catch (Exception e) {
						System.out.println("Error parsing line: " + e.getMessage());
					}
				}
				input.close();
			}
		} catch (IOException e) {
			System.out.println("Error accessing file: " + e.getMessage());
		}
	}

	/**
	 * Launches the main application menu.
	 */
	private void launchApp() {
		menu.printWelcomeMessage();
		int select;
		do {
			select = menu.showMainMenu();
			switch (select) {
			case 1:
				// TODO: Implement functionality for option 1
				break;
			case 2:
				// TODO: Implement functionality for option 2
				break;
			case 3:
				// TODO: Implement functionality for option 3
				break;
			case 4:
				System.out.println("Exiting application...");
				break;
			default:
				System.out.println("Invalid input... Try Again"); // Prompt for valid input
				break;
			}
		} while (select != 4);
	}
}
