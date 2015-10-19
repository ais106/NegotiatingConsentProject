package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by anna on 24/08/15.
 */
public class PhoneInfo {

    //the IMEI code
    private String deviceid;
    //the phone number string for line 1, for example, the MSISDN for a GSM phone
    private String phonenumber;
    //the software version number for the device, for example, the IMEI/SV for GSM phones
    private String softwareversion;
    //the alphabetic name of current registered operator.
    private String operatorname;
    //the ISO country code equivalent for the SIM provider's country code.
    private String simcountrycode;
    //Service Provider Name (SPN).
    private String simoperator;
    //serial number of the SIM
    private String simserialno;
    //the unique subscriber ID, for example, the IMSI for a GSM phone
    private String subscriberid;
    //the type indicating the radio technology (network type) currently in use on the device for data transmission.
    //EDGE,GPRS,UMTS  etc
    private String networktype;
    //indicating the device phone type. This indicates the type of radio used to transmit voice calls
    //GSM,CDMA etc
    private String phonetype;
    // Locationss
    private String phonelocation;

    //Empty constructor
    public PhoneInfo(){

    }


    //Constructor
    public PhoneInfo(String deviceid,String phonenumber, String softwareversion, String operatorname, String simcountrycode,
                     String simoperator, String simserialno, String subscriberid, String networktype, String phonetype, String phonelocation){

        this.deviceid = deviceid;
        this.phonenumber = phonenumber;
        this.softwareversion = softwareversion;
        this.operatorname = operatorname;
        this.simcountrycode = simcountrycode;
        this.simoperator = simoperator;
        this.simserialno = simserialno;
        this.subscriberid = subscriberid;
        this.networktype = networktype;
        this.phonetype = phonetype;
        this.phonelocation = phonelocation;
    }



    public String getPhoneNumber() { return this.phonenumber; }

    public void setPhoneNumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getOperatorname() {
        return this.operatorname;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    public String getSoftwareversion() {
        return this.softwareversion;
    }

    public void setSoftwareversion(String softwareversion) {
        this.softwareversion = softwareversion;
    }

    public String getDeviceId() {
        return this.deviceid;
    }

    public void setDeviceId(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getSimcountrycode() {
        return this.simcountrycode;
    }

    public void setSimcountrycode(String simcountrycode) {
        this.simcountrycode = simcountrycode;
    }

    public String getSimoperator() {
        return this.simoperator;
    }

    public void setSimoperator(String simoperator) {
        this.simoperator = simoperator;
    }

    public String getSimserialno() {
        return this.simserialno;
    }

    public void setSimserialno(String simserialno) {
        this.simserialno = simserialno;
    }

    public String getSubscriberid() {
        return this.subscriberid;
    }

    public void setSubscriberid(String subscriberid) {
        this.subscriberid = subscriberid;
    }

    public String getNetworktype() {
        return this.networktype;
    }

    public void setNetworktype(String networktype) {
        this.networktype = networktype;
    }

    public String getPhonetype() {
        return this.phonetype;
    }

    public void setPhonetype(String phonetype) {
        this.phonetype = phonetype;
    }

    public String getPhonelocation() {
        return this.phonelocation;
    }

    public void setPhonelocation(String phonelocation) {
        this.phonelocation = phonelocation;
    }



}
