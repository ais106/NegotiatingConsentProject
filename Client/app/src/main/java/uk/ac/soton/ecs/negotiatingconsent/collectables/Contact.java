package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by Anna Soska on 21/07/15.
 */
public class Contact {

    //Contact fields

    public String phone;
    public String phoneType;
    public String email;
    public String emailType;
    public String logcall;
    public String name;
    public String imageUri;

    // Empty constructor
    public Contact(){
        super();

    }

    // constructor 2
    public Contact(String name, String phone, String phoneType, String email, String emailType, String logcall, String imageUri){
        this.name = name;
        this.phone = phone;
        this.phoneType = phoneType;
        this.email = email;
        this.emailType = emailType;
        this.logcall = logcall;
        this.imageUri = imageUri;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() { return this.phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getPhoneType() {
        return this.phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailType() {
        return this.emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getLogcall() {
        return this.logcall;
    }

    public void setLogcall(String logcall) {
        this.logcall = logcall;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }


}
