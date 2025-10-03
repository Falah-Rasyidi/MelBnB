package menu;

import java.util.ArrayList;
import java.util.Scanner;

import booking.BookProperty;
import db.PropertyDatabase;
import db.model.Property;

public class RatingMenu extends Menu {
    // Initialise property database for this class
    public RatingMenu(PropertyDatabase propertyDatabase) {
        super(propertyDatabase);
    }
    
    public void initialise() {
        // Reset search builder and specify that we're searching by rating
        searchBuilder.setLength(0);
        searchBuilder.append("RATING,");

        double input = getUserRating();
        
        // Assume that 5.0 is the max rating
        while (input < 0.0 || input > 5.0) {
            System.out.println("Please input a rating between 0.0 and 5.0\n");
            input = getUserRating();
        }
        
        // Retrieve property database and perform searching based on the input
        // Populate result list, which will be used in the menu
        PropertyDatabase propertyDatabase = this.getPropertyDatabase();
        searchBuilder.append(input);
        resultList = new ArrayList<Property>(propertyDatabase.search(searchBuilder.toString()));
    }
    
    public void printMenu() {
        // An enhanced for loop wasn't used because an index variable is required
        printBanner("Select from matching list");
        for (int i = 0; i < resultList.size(); i++) {
            System.out.printf("   %d) %s%n", i + 1, resultList.get(i).getPropertyName());
        }
        System.out.printf("   %d) Go to main menu%n", resultList.size() + 1);
        System.out.print("Please select: ");
    }

    public boolean handleInput() {
        int choice = getUserInput();

        // Ensure that input isn't zero or above the allowed range
        boolean invalidChoice = choice < 1 || choice > resultList.size() + 1;
        if (invalidChoice) {
            System.out.println("Please select a valid menu option");
        }
        else if (choice == resultList.size() + 1) {
            // Exit if user chooses the 'Go to main menu' option
            return true;
        }
        else {
            // Commence booking
            Property property = resultList.get(choice - 1);
            BookProperty booking = new BookProperty(property);
            booking.book();

            // Return to main menu after booking finishes
            return true;
        }

        return false;
    }

    // Helper method
    public double getUserRating() {
        Scanner scnr = new Scanner(System.in);
        double retValue = -1.0;

        System.out.print("Please provide the minimum rating: ");
        String input = scnr.nextLine();

        try {
            retValue = Double.parseDouble(input);
        } catch (NumberFormatException e) {
        }

        return retValue;
    }
}