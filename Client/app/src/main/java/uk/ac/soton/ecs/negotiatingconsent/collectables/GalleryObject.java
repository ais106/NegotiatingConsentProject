package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by anna on 08/09/15.
 */
public class GalleryObject {

    private String bmpString;

    // Empty constructor
    public GalleryObject(){

    }

    // constructor 2
    public GalleryObject(String bmpString){
        this.bmpString = bmpString;
    }


    public String getBmpString() {
        return this.bmpString;
    }

    public void setBmpString(String bmpString){this.bmpString=bmpString;}
}
