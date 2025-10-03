package menu;

import db.PropertyDatabase;

public class MainMenu extends Menu {
    private LocationMenu locationMenu;
    private TypeMenu typeMenu;
    private RatingMenu ratingMenu;

    // Pass the property database to the other menus
    public MainMenu(PropertyDatabase propertyDatabase) {
        super(propertyDatabase);
        this.locationMenu = new LocationMenu(propertyDatabase);
        this.typeMenu = new TypeMenu(propertyDatabase);
        this.ratingMenu = new RatingMenu(propertyDatabase);
    }

    public void initialise() {
        // Main menu doesn't require any initialisation procedure
    }

    public void printMenu() {
        printBanner("Select from main menu");
        System.out.println("   1) Search by location");
        System.out.println("   2) Browse by type of place");
        System.out.println("   3) Filter by rating");
        System.out.println("   4) Exit");
        System.out.print("Please select: ");
    }

    public boolean handleInput() {
        // All functionality encapsulated in 'run' method
        int choice = getUserInput();
        switch (choice) {
            case 1:
                locationMenu.run();
                break;
            case 2:
                typeMenu.run();
                break;
            case 3:
                ratingMenu.run();
                break;
            case 4:
                // Signal that we want to exit
                return true;
            default:
                System.out.println("Please select a valid menu option");
                break;
        }
        
        return false;
    }
}