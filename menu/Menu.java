package menu;
import java.util.ArrayList;
import java.util.Scanner;

import db.PropertyDatabase;
import db.model.Property;

// Contains attributes and methods common to all subclasses
public abstract class Menu {
    private final String BANNER_DECORATOR = '\n' + new String(new char[80]).replace('\u0000', '-');
    protected PropertyDatabase propertyDatabase; // Access the property database
    protected StringBuilder searchBuilder;       // Build the query to be passed to the 'search' method
    protected ArrayList<Property> resultList;    // Hold properties returned by 'search' method

    // All subclasses must initialise the property database and search builder
    public Menu(PropertyDatabase propertyDatabase) {
        this.propertyDatabase = propertyDatabase;
        this.searchBuilder = new StringBuilder();
    }

    // Handles inputs for the menus
    public int getUserInput() {
        Scanner scnr = new Scanner(System.in);
        String input = scnr.nextLine();
        int retValue = -1;

        try {
            retValue = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Error message is placed in subclasses
            // Specific to each subclass
        }

        return retValue;
    }

    // Utility method
    public void printBanner(String bannerText) {
        System.out.println(BANNER_DECORATOR);
        System.out.printf("> %s", bannerText);
        System.out.println(BANNER_DECORATOR);
    }

    // 'run' method bundles 'initialise', 'printMenu', and 'handleInput' methods together
    public void run() {
        boolean exit = false;
        initialise();
        do {
            printMenu();
            exit = handleInput();
        } while (!exit);
    }

    public PropertyDatabase getPropertyDatabase() {
        return this.propertyDatabase;
    }

    // Common functionality implemented by sub classes
    // CONTRACT: These methods shouldn't be called by themselves.
    //           Instead, they're ran together as part of the 'run' method.
    public abstract void initialise();

    public abstract void printMenu();

    public abstract boolean handleInput();
}