package application;

import controller.ToyManager;

/**
 * The entry point for the Toy Store application. This class initializes the
 * ToyManager, which is responsible for loading toy data, managing user
 * interactions, and launching the main menu.
 * 
 * 
 * @author Justin, Fatema, Manveet
 * @version 2.0
 */
public class AppDriver {

	/**
	 * The main method to start the application. Initializes an instance of
	 * ToyManager, which handles loading data from file, managing inventory, and
	 * facilitating user interactions through the main menu.
	 *
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		new ToyManager(); // Initialize ToyManager to load data and start the application
	}
}
