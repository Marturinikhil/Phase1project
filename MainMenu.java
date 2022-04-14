package lockedMe.com.app;
import java.util.*;

public class MainMenu {
	public static void welcomeScreen(String applicationName, String developer) {
		String details = String.format("_____________________________________________________________\n"
				+ "Welcome to the %s. \n" + "LOCKEDME.COM application was developed by %s.\n"
				+ "___________________________________________________________________\n", applicationName, developer);
		
		System.out.println(details);

	}

	public static void mainMenuOptions() {
		String mainMenu = "\n\n------Select any option to perform and press Enter-------\n\n"
				+ "1) Retrieve all files inside the folder in ascending order \n"
				+ "2) Business Operation menu for Files \n"
				+ "3) Exit program\n";
		System.out.println(mainMenu);

	}

	public static void businessMenuOptions() {
		String businessMenu = "\n\n-----Select any option to perform and press Enter--------\n\n"
				+ "1) Add a file to the folder\n"
				+ "2) Delete a file from the folder\n"
				+ "3) Search for a file from the folder\n"
				+ "4) Redirect to Previous Menu\n"
				+ "5) Close and Exit program\n";

		System.out.println(businessMenu);
	}

	public static void handleMainMenuOperations() {
		boolean running = true;
		Scanner sc = new Scanner(System.in);
		do {
			try {
				mainMenuOptions();
				int input = sc.nextInt();

				switch (input) {
				case 1:
					BusinessMenu.displayAllFiles("main");
					break;
				case 2:
					handleBusinessMenuOperations();
					break;
				case 3:
					System.out.println("Program exited successfully.");
					running = false;
					sc.close();
					System.exit(0);
					break;
				default:
					System.out.println("Please select a valid option from above.");
				}
			} catch (Exception e) {
				System.out.println(e.getClass().getName());
				handleMainMenuOperations();
			}
		} while (running == true);
	}

	public static void handleBusinessMenuOperations() {
		boolean running = true;
		Scanner sc = new Scanner(System.in);
		do {
			try {
				businessMenuOptions();
				BusinessMenu.generateMainIfNotExist("main");

				int input = sc.nextInt();
				String file;
				switch (input) {
				case 1:
					// File Add
					System.out.println("Enter the name of the file that needs to be added to the folder");
					file = sc.next();
					BusinessMenu.generateFile(file, sc);

					break;
				case 2:
					// delete
					System.out.println("Enter the name of the file that needs to be deleted from  folder");
					file = sc.next();

					BusinessMenu.generateMainIfNotExist("main");
					List<String> deleteFiles = BusinessMenu.showFileLocations(file, "main");

					String deleteMsg = "\nSelect index of which file to delete?"
							+ "\n(Enter 1 if you want to delete)";
					System.out.println(deleteMsg);

					int index = sc.nextInt();

					if (index != 0) {
						BusinessMenu.deleteFileInLoop(deleteFiles.get(index - 1));
					} else {

						// delete all files displayed
						for (String path : deleteFiles) {
							BusinessMenu.deleteFileInLoop(path);
						}
					}

					break;
				case 3:
					// Search
					System.out.println("Enter the name of the file needs to be searched from folder");
					String fileName = sc.next();

					BusinessMenu.generateMainIfNotExist("main");
					BusinessMenu.showFileLocations(fileName, "main");

					break;
				case 4:
					// redirect to Previous menu
					return;
				case 5:
					// close and exit
					System.out.println("Program closed successfully.");
					running = false;
					sc.close();
					System.exit(0);
				default:
					System.out.println("Please select a valid option.");
				}
			} catch (Exception e) {
				System.out.println(e.getClass().getName());
				handleBusinessMenuOperations();
			}
		} while (running == true);
	}
public static void main(String[] args) {
		
		// Create "main" folder if not present in current folder structure
		BusinessMenu.generateMainIfNotExist("main");
		
		welcomeScreen("LockedMe.com", "Marturi Nikhil");
		
		handleMainMenuOperations();
	}
}



