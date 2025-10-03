package menu;

import java.util.ArrayList;

import db.PropertyDatabase;
import db.model.Property;

public class TypeMenu extends Menu {
    private TypeListMenu typeListMenu;
    private ArrayList<String> typeSet;

    // Initialise property database for this class
    // 'typeSet' mimics a set
    public TypeMenu(PropertyDatabase propertyDatabase) {
        super(propertyDatabase);
        typeSet = new ArrayList<>();
    }
    
    public void initialise() {
        searchBuilder.setLength(0);
        searchBuilder.append("TYPE,N/A");
        
        // A set wasn't used here because ordering was needed (to allow indexing into the list when displaying to the terminal)
        // And also for consistent ordering when shown on the menu
        PropertyDatabase propertyDatabase = getPropertyDatabase();
        resultList = new ArrayList<Property>(propertyDatabase.search(searchBuilder.toString()));
        for (Property property : resultList) {
            if (!typeSet.contains(property.getPropertyType())) {
                typeSet.add(property.getPropertyType());
            }
        }
    }
    
    public void printMenu() {
        // An enhanced for loop wasn't used because an index variable is required
        printBanner("Browse by type of place");
        for (int i = 0; i < typeSet.size(); i++) {
            System.out.printf("   %d) %s%n", i + 1, typeSet.get(i));
        }
        System.out.printf("   %d) Go to main menu%n", typeSet.size() + 1);
        System.out.print("Please select: ");
    }

    public boolean handleInput() {
        int choice = getUserInput();

        // Ensure that input isn't zero of above the allowed range
        boolean invalidChoice = choice < 1 || choice > typeSet.size() + 1;
        if (invalidChoice) {
            System.out.println("Please select a valid menu option");
        }
        else if (choice == typeSet.size() + 1) {
            // Exit if user chooses the 'Go to main menu' option
            return true;
        }
        else {
            // After a property type is chosen, go to menu listing all properties matching that type
            typeListMenu = new TypeListMenu(getPropertyDatabase(), typeSet.get(choice - 1));
            typeListMenu.run();

            return true;
        }
        
        return false;
    }
}