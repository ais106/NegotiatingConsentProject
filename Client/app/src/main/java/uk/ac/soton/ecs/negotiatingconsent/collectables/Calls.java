package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by Anna Soska on 28/09/15.
 */
public class Calls {

    String name ="";
    String phNumber ="";
    String callType ="";
    String callDate ="";
    String callDuration ="";

    // Empty constructor
    public Calls(){

    }

    // constructor 2
    public Calls(String name, String phNumber, String callType, String callDate, String callDuration){
        this.name = name;
        this.phNumber = phNumber;
        this.callType = callType;
        this.callDate = callDate;
        this.callDuration = callDuration;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhNumber() { return this.phNumber; }

    public void setPhNumber(String phNumber) { this.phNumber = phNumber; }

    public String getCallType() {
        return this.callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallDatel() {
        return this.callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallDuration() {
        return this.callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }




}
