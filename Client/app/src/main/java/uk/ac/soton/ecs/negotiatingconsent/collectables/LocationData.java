package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by anna on 24/08/15.
 */
public class LocationData {
    private double longitude;
    private double latitude;
    private String city;
    private String address;
    private String datestamp;

    // Empty constructor
    public LocationData(){


    }

    // constructor 2
    public LocationData(double latitude, double longitude, String city, String address, String datestamp){
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.address = address;
        this.datestamp = datestamp;
    }


    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDatestamp() {
        return this.datestamp;
    }

    public void setDatestamp(String datestamp) {
        this.datestamp = datestamp;
    }

}
