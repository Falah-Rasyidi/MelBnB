package db.model;
public class Property {
    private String propertyName;
    private String propertyLocation;
    private String propertyDescription;
    private String propertyType;
    private String propertyHost;
    private int maximumGuests;
    private double propertyRating;
    private double nightlyFee;
    private double serviceFee;
    private double cleaningFee;
    private double weeklyDiscount;

    public Property(String propertyName, String propertyLocation, String propertyDescription, String propertyType, String propertyHost, int maximumGuests, double propertyRating, double nightlyFee, double serviceFee, double cleaningFee, double weeklyDiscount) {
        this.propertyName = propertyName;
        this.propertyLocation = propertyLocation;
        this.propertyDescription = propertyDescription;
        this.propertyType = propertyType;
        this.propertyHost = propertyHost;
        this.maximumGuests = maximumGuests;
        this.propertyRating = propertyRating;
        this.nightlyFee = nightlyFee;
        this.serviceFee = serviceFee;
        this.cleaningFee = cleaningFee;
        this.weeklyDiscount = weeklyDiscount;
    }

    // Setters aren't present because we don't programmatically create or alter a property

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyLocation() {
        return propertyLocation;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getPropertyHost() {
        return propertyHost;
    }

    public int getMaximumGuests() {
        return maximumGuests;
    }

    public double getPropertyRating() {
        return propertyRating;
    }

    public double getNightlyFee() {
        return nightlyFee;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public double getCleaningFee() {
        return cleaningFee;
    }

    public double getWeeklyDiscount() {
        return weeklyDiscount;
    }
}