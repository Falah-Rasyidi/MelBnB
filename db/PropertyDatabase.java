package db;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import db.model.Property;

public class PropertyDatabase implements Searchable<Property> {
    // Add necessary attributes

    // Populated at the start of the program
    private List<Property> propertyList = new ArrayList<Property>();

    // Necessary methods

    // Abstracts the 'loadPropertyData' method
    public void loadData() {
        propertyList = new ProcessCSV().loadPropertyData();
    }

    /**
     * CONTRACT:
     * Parameter 'keyword' must be in the form <SEARCH_TYPE>,<SEARCH_TERM>
     * <SEARCH_TYPE> indicates what attribute the search method filters by
     * <SEARCH_TERM> is the specific term that the search method matches against
     */
    public List<Property> search(String keyword) {
        List<Property> resultSet = new ArrayList<>();

        Scanner input = new Scanner(keyword);
        input.useDelimiter(",");

        String searchType = input.next();
        String searchTerm = input.next();

        switch (searchType) {
            case "LOCATION":
                for (Property property : propertyList) {
                    if (property.getPropertyLocation().toLowerCase().contains(searchTerm)) {
                        resultSet.add(property);
                    }
                }
                break;
            // Performed automatically when the user clicks on the 'Browse by type of place' menu
            // A set (of property types) wasn't used because this method returns a collection of properties, and so strings by themselves can't be returned directly
            // Instead, simply return all properties and leave it to menu to filter it down to just a set of property types
            case "TYPE":
                for (Property property : propertyList) {
                    resultSet.add(property);
                }
                break;
            // Searches for all properties matching a specified type
            case "TYPE_LIST":
                for (Property property : propertyList) {
                    if (property.getPropertyType().equals(searchTerm)) {
                        resultSet.add(property);
                    }
                }
                break;
            case "RATING":
                double rating = Double.parseDouble(searchTerm);
                for (Property property : propertyList) {
                    if (property.getPropertyRating() >= rating) {
                        resultSet.add(property);
                    }
                }
                break;
            default:
                break;
        }

        return resultSet;
    }
}