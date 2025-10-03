import db.PropertyDatabase;
import menu.MainMenu;

// Program entry point
public class MelBnB {
    public static void main(String[] args) {
        // Create property database and populate it
        PropertyDatabase propertyDatabase = new PropertyDatabase();
        propertyDatabase.loadData();
        
        // Pass property database to main menu
        MainMenu mainMenu = new MainMenu(propertyDatabase);

        // Start main menu
        System.out.print("\nWelcome to Melbnb!");
        mainMenu.run();
    }
}