package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by anna on 21/08/15.
 */
public class DataClass {

    private String permission;
    private Collectables cl;

    public DataClass(String permission) {
        // Assignments should not re-declare the fields
        this.permission = permission;

    }

    private Collectables dataType(String permission){
        if(permission.equals("sms")){
            cl = new SmsBox();
            return cl;
        }
        else if(permission.equals("browser history")){
            cl = new BrowserHistory();
            return  cl;
        }
        else if(permission.equals("calendar")){
            cl = new CalendarBox();
            return  cl;
        }
        else if(permission.equals("location")){
            cl = new TrackLocation();
            return  cl;
        }

        else if(permission.equals("contacts")){
            cl = new ContactBox();
            return  cl;
        }

        else if(permission.equals("phonestate")){
            cl = new PhoneStatus();
            return  cl;
        }
        else if(permission.equals("gallery")){
            cl = new GalleryImage();
            return  cl;
        }

        else {
            return new UnknownDataClass();
        }
    }



    public void saveDataLocally() {

        dataType(permission).saveDataLocally();

    }


    public boolean checkData() {

        return dataType(permission).dataExist();
    }

    

}




