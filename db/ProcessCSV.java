package db;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import db.model.Property;

public class ProcessCSV {
    private final String CSV_FILE = "db/Melbnb.csv";
    
    public ArrayList<Property> loadPropertyData() {
        ArrayList<Property> properties = new ArrayList<Property>();

        try {
            Scanner scnr = new Scanner(new File(CSV_FILE), StandardCharsets.UTF_8);

            // Consume header row
            scnr.nextLine();

            while (scnr.hasNext()) {
                String line = scnr.nextLine();

                Scanner row = new Scanner(line);
                row.useDelimiter(",");

                String propertyName = row.next();
                String propertyLocation = row.next();
                String propertyDescription = row.next();
                String propertyType = row.next();
                String propertyHost = row.next();
                int maximumGuest = row.nextInt();
                double propertyRating = row.nextDouble();
                double nightlyFee = row.nextDouble();
                double serviceFee = row.nextDouble();
                double cleaningFee = row.nextDouble();
                double weeklyDiscsount = row.nextDouble();

                // All properties are sourced from the spreadsheet and not created within the program itself
                properties.add(new Property(
                    propertyName,
                    propertyLocation,
                    propertyDescription,
                    propertyType,
                    propertyHost,
                    maximumGuest, 
                    propertyRating,
                    nightlyFee,
                    serviceFee,
                    cleaningFee,
                    weeklyDiscsount
                ));
            }
        } catch(FileNotFoundException e) {
            System.out.println("Unable to locate the specified CSV");
        } catch (IOException e) {
            System.out.println("A problem occurred with I/O");
        }

        return properties;
    }
}