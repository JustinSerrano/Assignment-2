/**
 * 
 */
package controller;

import view.AppMenu;

/**
 * 
 * @author Justin
 * @version 1
 */
public class AppManager {

	private AppMenu menu = new AppMenu();

	public AppManager() {

//		loadData();
		launchApp(); // Launch the main application menu

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
