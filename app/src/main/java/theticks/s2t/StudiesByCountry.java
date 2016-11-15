package theticks.s2t;

/**
 * Created by rpadurariu on 15.11.2016
 */

public class StudiesByCountry {
    private String countryName;
    private int numberOfStudies;

    public StudiesByCountry(String countryName, int numberOfStudies) {
        this.countryName = countryName;
        this.numberOfStudies = numberOfStudies;
    }

    // setter
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setNumberOfStudies(int numberOfStudies) {
        this.numberOfStudies = numberOfStudies;
    }

    // getter
    public String getCountryName() {
        return this.countryName;
    }

    public int getNumberOfStudies() {
        return this.numberOfStudies;
    }
}
