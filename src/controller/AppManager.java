/**
 * 
 */
package controller;

import java.io.*;
import java.util.*;

import view.AppMenu;

/**
 * 
 * @author Justin
 * @version 1
 */
public class AppManager {

	private static String FILE_PATH = "res/toys.txt";
	private AppMenu menu = new AppMenu();

	public AppManager() {

		loadData();
		launchApp(); // Launch the main application menu

	}
	
	private void loadData() {
		
		try {
			File file = new File(FILE_PATH);
			
			if (!file.exists()) {
				file.createNewFile();
			} else {
				Scanner input = new Scanner(file);
				
				while (input.hasNext()) {
					
				}
			}
			
		} catch (IOException e) {
			
		}
	}

	private void launchApp() {
		menu.printWelcomeMessage();
		int select;
		do {
			select = menu.showMainMenu();
			switch (select) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			default:// Invalid input
				System.out.println("Invalid input... Try Again"); // Prompt for valid input
				break;
			}
		} while (select != 4);
	}

}
