package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by Anna Soska on 30/06/15.
 */
public class Url {

    private String url; // Address of the website
    private String visits; // Number of visits
    private String title;  // title of the website
    private String lastVisit; // The date of last visit
    private byte[] icon;

    // Empty constructor
    public Url(String string, String cursorString, String s){

    }

    // constructor
    public Url( String url){

        this.url = url;
    }
    // constructor 2
    public Url(String title, String url, String visits, String lastVisit, byte[] icon){
        this.url = url;
        this.visits = visits;
        this.title = title;
        this.lastVisit = lastVisit;
        this.icon = icon;
    }

    // Get Number of Visits
    public String getVisits(){
        return this.visits;
    }

    // setting number of Visits
    public void setVisits(String visits){
        this.visits = visits;
    }

    // Get Title of the website
    public String getTitle(){
        return this.title;
    }

    // Set Title of the Website
    public void setTitle(String title){
        this.title = title;
    }


    // Get Url Link
    public String getUrl(){
        return this.url;
    }

    // Set Url
    public void setUrl(String url){
        this.url = url;
    }

    //Get Date of Last Visit
    public String getLastVisit(){
        return this.lastVisit;
    }

    // Set Url
    public void setLastVisit(String lastVisit){
        this.lastVisit = lastVisit;
    }

    //Get Date of Last Visit
    public byte[] getIcon(){
        return this.icon;
    }

    // Set Url
    public void setIcon(byte[] icon){
        this.icon = icon;
    }

    // Get Url Link
    public String getUrlAll(){
        String allUrls = "Title: "+this.title +
                         "\n Address: "+ this.url +
                         "\n Date of last visit: " + this.lastVisit +
                         "\n Number of visits: " + this.visits;
        return allUrls;
    }




}
