package lockedMe.com.app;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BusinessMenu {
	public static void generateMainIfNotExist(String folderName) {
	File file = new File(folderName);
	if (!file.exists()) {
		file.mkdirs();
	}
}

public static void displayAllFiles(String path) {
	generateMainIfNotExist("main");
	System.out.println("Displaying all  the files inside the main folder in the directory structure in ascending order\n");

	List<String> fileNames = showFilesInDirectory(path, 0, new ArrayList<String>());
	System.out.println("Displaying files in the ascending order\n");
	Collections.sort(fileNames);
	fileNames.stream().forEach(System.out::println);
}

public static List<String> showFilesInDirectory(String path, int indent, List<String> listOfFiles) {
	File directory = new File(path);
	File[] files = directory.listFiles();
	List<File> listFiles = Arrays.asList(files);
	Collections.sort(listFiles);
	if (files != null && files.length > 0) {
		for (File file : listFiles) {
			if (file.isDirectory()) {
				System.out.println("`-- " + file.getName());
				listOfFiles.add(file.getName());
				showFilesInDirectory(file.getAbsolutePath(), indent + 1, listOfFiles);
			} else {
				System.out.println("|-- " + file.getName());
				listOfFiles.add(file.getName());
			}
		}
	} else {
		System.out.println("|-- Empty Directory");
	}
	System.out.println();
	return listOfFiles;
}

public static void generateFile(String fileToAdd, Scanner sc) {
	generateMainIfNotExist("main");
	Path pathToFile = Paths.get("./main/" + fileToAdd);
	try {
		Files.createDirectories(pathToFile.getParent());
		Files.createFile(pathToFile);
		System.out.println(fileToAdd + " created successfully in the main");
		System.out.println("Would you like to add content to the file if yes press Y or N? (Y/N)");
		String choice = sc.next().toLowerCase();
		sc.nextLine();
		if (choice.equals("y")) {
			System.out.println("\n\nInput content and press enter\n");
			String content = sc.nextLine();
			Files.write(pathToFile, content.getBytes());
			System.out.println("\nContent written to file " + fileToAdd);
			System.out.println("Content can be read using Notepad or Notepad++");
		}

	} catch (IOException e) {
		System.out.println("Failed to create file " + fileToAdd);
		System.out.println(e.getClass().getName());
	}
}

public static List<String> showFileLocations(String fileName, String path) {
	List<String> fileNames = new ArrayList<>();
	searchFileInLoop(path, fileName, fileNames);

	if (fileNames.isEmpty()) {
		System.out.println("\n\n------Could not find any file with the given file name \"" + fileName + "\" -----\n\n");
	} else {
		System.out.println("\n\nFound file at the location:");

		List<String> files = IntStream.range(0, fileNames.size())
				.mapToObj(index -> (index + 1) + ": " + fileNames.get(index)).collect(Collectors.toList());

		files.forEach(System.out::println);
	}

	return fileNames;
}

public static void searchFileInLoop(String path, String fileName, List<String> fileNames) {
	File directory = new File(path);
	File[] files = directory.listFiles();
	List<File> filesList = Arrays.asList(files);

	if (files != null && files.length > 0) {
		for (File file : filesList) {
			if (file.getName().startsWith(fileName)) {
				fileNames.add(file.getAbsolutePath());
			}
			if (file.isDirectory()) {
				searchFileInLoop(file.getAbsolutePath(), fileName, fileNames);
			}
		}
	}
}

public static void deleteFileInLoop(String path) {

	File currentFile = new File(path);
	File[] files = currentFile.listFiles();

	if (files != null && files.length > 0) {
		for (File file : files) {
			String fileName = file.getName() + " at " + file.getParent();
			if (file.isDirectory()) {
				deleteFileInLoop(file.getAbsolutePath());
			}
			if (file.delete()) {
				System.out.println(fileName + " deleted successfully");
			} else {
				System.out.println("Failed to delete the file " + fileName);
			}
		}
	}

	String currFileName = currentFile.getName() + " at " + currentFile.getParent();
	if (currentFile.delete()) {
		System.out.println(currFileName + " deleted successfully");
	} else {
		System.out.println("Failed to delete " + currFileName);
	}
}}

