package booking;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.model.Booking;
import db.model.Property;

public class BookProperty {
    private final String BANNER_DECORATOR = '\n' + new String(new char[80]).replace('\u0000', '-');
    private Property property;
    private Booking booking;

    // 'property' indicates which property was chosen
    // 'booking' keeps track of a booking's details
    public BookProperty(Property property) {
        this.property = property;
        this.booking = new Booking();
    }

    // The 'book' method bundles the 'getDates', 'printPropertyDetails', 'getPersonalDetails', and 'printBookingDetails' methods together
    public void book() {
        Scanner scnr = new Scanner(System.in);
        String input;

        printBanner("Provide dates");
        getDates();

        printBanner("Show property details");
        printPropertyDetails();
        // Return to main menu if the user answers 'N'
        do {
            System.out.print("Would you like to reserve the property (Y/N)? ");
            input = scnr.nextLine().toUpperCase();
            if (input.strip().toUpperCase().equals("N")) {
                return;
            }
        } while (input.isBlank() || !input.strip().toUpperCase().equals("Y"));

        printBanner("Provide personal information");
        getPersonalDetails();
        do {
            System.out.print("Confirm and pay (Y/N): ");
            input = scnr.nextLine().toUpperCase();
            if (input.strip().toUpperCase().equals("N")) {
                return;
            }
        } while (input.isBlank() || !input.strip().toUpperCase().equals("Y"));

        printBanner("""
        Congratulations! Your trip is booked. A receipt has been sent to your email.
          Details of your trip are shown below.
          Your host will contact you before your trip. Enjoy your stay!""");
        printBookingDetails();

        // Return to main menu after booking
    }

    public void getDates() {
        Scanner scnr = new Scanner(System.in);
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.now();

        // Outer while loop repeats if check-in date is after check out date
        boolean done = false;
        while (!done) {
            // Repeat if incorrectly formatted date or numbers are invalid
            boolean checkInDone = false;
            while (!checkInDone) {
                System.out.print("Please provide check-in date (dd/mm/yyyy): ");
                String input = scnr.nextLine();
    
                // Ensure that date is in form dd/mm/yyyy
                Pattern pattern = Pattern.compile("([0-9]{1,2}\\/){2}[0-9]{4}");
                Matcher matcher = pattern.matcher(input);
    
                if (!matcher.find()) {
                    System.out.println("Please provide a check-in date in the format dd/mm/yyyy\n");
                    continue;
                }
    
                // If conforms to regex, make sure numbers are valid
                Scanner numberScanner = new Scanner(input);
                numberScanner.useDelimiter("/");
    
                int dayOfMonth = numberScanner.nextInt();
                int month = numberScanner.nextInt();
                int year = numberScanner.nextInt();
    
                try {
                    checkInDate = LocalDate.of(year, month, dayOfMonth);
                } catch (DateTimeException e) {
                    System.out.println("Please input valid days, months, and years\n");
                    continue;
                }
    
                checkInDone = true;
            }
    
            // Repeat if incorrectly formatted date or numbers are invalid
            boolean checkOutDone = false;
            while (!checkOutDone) {
                System.out.print("Please provide check out date (dd/mm/yyyy): ");
                String input = scnr.nextLine();

                Pattern pattern = Pattern.compile("([0-9]{1,2}\\/){2}[0-9]{4}");
                Matcher matcher = pattern.matcher(input);

                if (!matcher.find()) {
                    System.out.println("Please provide a check out date in the format dd/mm/yyyy\n");
                    continue;
                }

                // if conforms to regex, make sure numbers are valid
                Scanner numberScanner = new Scanner(input);
                numberScanner.useDelimiter("/");

                int dayOfMonth = numberScanner.nextInt();
                int month = numberScanner.nextInt();
                int year = numberScanner.nextInt();

                try {
                    checkOutDate = LocalDate.of(year, month, dayOfMonth);
                } catch (DateTimeException e) {
                    System.out.println("Please input valid days, months, and years\n");
                    continue;
                }

                checkOutDone = true;
            }

            // Set booking information as we proceed with booking
            booking.setCheckInDate(checkInDate);
            booking.setCheckOutDate(checkOutDate);

            // Ensure that stay is at least a day
            long stayDuration = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
            if (stayDuration <= 0) {
                System.out.println("Stay must be at least one day\n");
                continue;
            }

            done = true;
        }
    }

    public void printPropertyDetails() {
        long stayDuration = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        double discountedNightlyFee = stayDuration >= 7 ? this.property.getNightlyFee() * (1 - (this.property.getWeeklyDiscount() / 100.0)) : this.property.getNightlyFee();
        double price = this.property.getNightlyFee() * stayDuration;
        double discountedPrice = stayDuration >= 7 ? discountedNightlyFee * stayDuration : price;
        double serviceFee = this.property.getServiceFee() * stayDuration;
        double cleaningFee = this.property.getCleaningFee();
        double totalPrice = discountedPrice + serviceFee + cleaningFee;

        System.out.printf("%-26s%s hosted by %s%n", "Property:", this.property.getPropertyName(), this.property.getPropertyHost());
        System.out.printf("%-26s%s%n", "Type of place:", this.property.getPropertyType());
        System.out.printf("%-26s%s%n", "Location:", this.property.getPropertyLocation());
        System.out.printf("%-26s%.2f%n", "Rating:", this.property.getPropertyRating());
        System.out.printf("%-26s%s%n", "Description:", this.property.getPropertyDescription());
        System.out.printf("%-26s%d%n", "Number of guests:", this.property.getMaximumGuests());
        System.out.printf("%-26s$%.2f ($%.2f * %d %s)%n", "Price:", price, this.property.getNightlyFee(), stayDuration, stayDuration == 1 ? "night" : "nights");
        System.out.printf("%-26s$%.2f ($%.2f * %d %s)%n", "Discounted price:", discountedPrice, discountedNightlyFee, stayDuration, stayDuration == 1 ? "night" : "nights");
        System.out.printf("%-26s$%.2f ($%.2f * %d %s)%n", "Service fee:", serviceFee, this.property.getServiceFee(), stayDuration, stayDuration == 1 ? "night" : "nights");
        System.out.printf("%-26s$%.2f%n", "Cleaning fee:", cleaningFee);
        System.out.printf("%-26s$%.2f%n", "Total:", totalPrice);

        this.booking.setPayment(totalPrice);
    }

    public void getPersonalDetails() {
        Scanner scnr = new Scanner(System.in);
        
        boolean nameDone = false;
        while (!nameDone) {
            System.out.print("Please provide your given name: ");
            String firstName = scnr.nextLine();

            System.out.print("Please provide your surname: ");
            String lastName = scnr.nextLine();

            // First name and last name should only contain letters
            if (!verifyName(firstName) || !verifyName(lastName)) {
                System.out.println("Please input names consisting of only letters\n");
                continue;
            }
            
            this.booking.setFirstName(firstName);
            this.booking.setLastName(lastName);
            
            nameDone = true;
        }

        boolean emailDone = false;
        while (!emailDone) {
            System.out.print("Please provide your email address: ");
            String email = scnr.nextLine();

            // Ensure that email conforms to regex pattern
            if (!verifyEmail(email)) {
                System.out.println("Please input a valid email\n");
                continue;
            }

            this.booking.setEmail(email);
            
            emailDone = true;
        }

        boolean guestsDone = false;
        while (!guestsDone) {
            System.out.print("Please provide number of guests: ");
            String numberOfGuests = scnr.nextLine();
            int guests = 0;

            try {
                // First make sure that guests input is a number
                // Then, ensure that it's less than or equal to the property's maximum number of guests
                guests = Integer.parseInt(numberOfGuests);
                if (!verifyGuests(guests)) {
                    System.out.printf("Please enter a number within the specified maximum (%d %s)%n%n", this.property.getMaximumGuests(), this.property.getMaximumGuests() == 1 ? "guest" : "guests");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number\n");
                continue;
            }
            
            this.booking.setNumberOfGuests(guests);
            
            guestsDone = true;
        }
    }

    public void printBookingDetails() {
        System.out.printf("%-26s%s%n", "Name:", this.booking.getFirstName() + " " + this.booking.getLastName());
        System.out.printf("%-26s%s%n", "Email:", this.booking.getEmail());
        System.out.printf("%-26s%s hosted by %s%n", "Your stay:", this.property.getPropertyName(), this.property.getPropertyHost());
        System.out.printf("%-26s%d %s%n", "Who's coming:", this.booking.getNumberOfGuests(), this.booking.getNumberOfGuests() == 1 ? "guest" : "guests");
        System.out.printf("%-26s%s%n", "Check-in date:", this.booking.getCheckInDate().toString());
        System.out.printf("%-26s%s%n", "Checkout date:", this.booking.getCheckOutDate().toString());
        System.out.printf("%-26s$%.2f%n", "Total payment:", this.booking.getPayment());
    }

    public boolean verifyName(String name) {
        char[] chars = name.toCharArray();

        // Prevents user from just pressing enter on input
        if (name.isBlank()) {
            return false;
        }

        // Name must only contain letters
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public boolean verifyEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public boolean verifyGuests(int guests) {
        return guests >= 0 && guests <= this.property.getMaximumGuests();
    }

    public void printBanner(String bannerText) {
        System.out.println(BANNER_DECORATOR);
        System.out.printf("> %s", bannerText);
        System.out.println(BANNER_DECORATOR);
    }
}