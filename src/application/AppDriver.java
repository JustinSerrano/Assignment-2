package application;

import controller.ToyManager;

/**
 * The entry point for the Toy Store application.
 * This class initializes the ToyManager, which handles loading data and launching the main menu.
 * 
 * @author Justin, Fatema, Manveet
 * @version 1.0
 */
public class AppDriver {

    /**
     * The main method to start the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new ToyManager(); // Initialize ToyManager to load data and start the application
    }
}
