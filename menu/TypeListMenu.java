package menu;

import java.util.ArrayList;

import booking.BookProperty;
import db.PropertyDatabase;
import db.model.Property;

public class TypeListMenu extends Menu {
    private String propertyType;

    // Initialise property database for this class
    // 'propertyType' tracks what property was chosen from the TypeMenu class
    public TypeListMenu(PropertyDatabase propertyDatabase, String propertyType) {
        super(propertyDatabase);
        this.propertyType = propertyType;
    }

    public void initialise() {
        // Reset search builder and specify that we're searching by property type
        searchBuilder.setLength(0);
        searchBuilder.append("TYPE_LIST,");

        // Retrieve property database and perform searching based on the input
        // Populate result list, which will be used in the menu
        PropertyDatabase propertyDatabase = getPropertyDatabase();
        searchBuilder.append(this.propertyType);
        resultList = new ArrayList<Property>(propertyDatabase.search(searchBuilder.toString()));
    }

    public void printMenu() {
        // An enhanced for loop wasn't used because an index variable is required
        printBanner("Select from " + this.propertyType.toLowerCase() + " list");
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
}