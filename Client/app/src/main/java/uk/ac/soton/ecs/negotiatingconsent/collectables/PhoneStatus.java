package uk.ac.soton.ecs.negotiatingconsent.collectables;

import android.content.ContentResolver;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.helpers.AppCoreData;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbDataHandler;

/**
 * Created by anna on 24/08/15.
 */
public class PhoneStatus implements Collectables {


    Context context = AppCoreData.Context();
    ContentResolver cr = AppCoreData.ContentResolver();

        /*
         * Display the telephony related information
         * */
        public PhoneInfo getPhoneInfo() {

            //access to the telephony services
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //Get the IMEI code
            String deviceid = tm.getDeviceId();
            //Get  the phone number string for line 1, for example, the MSISDN for a GSM phone
            String phonenumber = tm.getLine1Number();
            //Get  the software version number for the device, for example, the IMEI/SV for GSM phones
            String softwareversion = tm.getDeviceSoftwareVersion();
            //Get  the alphabetic name of current registered operator.
            String operatorname = tm.getNetworkOperatorName();
            //Get  the ISO country code equivalent for the SIM provider's country code.
            String simcountrycode = tm.getSimCountryIso();
            //Get  the Service Provider Name (SPN).
            String simoperator = tm.getSimOperatorName();
            //Get  the serial number of the SIM, if applicable. Return null if it is unavailable.
            String simserialno = tm.getSimSerialNumber();
            //Get  the unique subscriber ID, for example, the IMSI for a GSM phone
            String subscriberid = tm.getSubscriberId();
            //Get the type indicating the radio technology (network type) currently in use on the device for data transmission.
            //EDGE,GPRS,UMTS  etc
            String networktype = getNetworkTypeString(tm.getNetworkType());
            //indicating the device phone type. This indicates the type of radio used to transmit voice calls
            //GSM,CDMA etc
            String phonetype = getPhoneTypeString(tm.getPhoneType());
            // Location
            String phonelocation = tm.getCellLocation().toString();


            PhoneInfo phoneInfo = new PhoneInfo(deviceid, phonenumber, softwareversion,
                                                operatorname, simcountrycode, simoperator, simserialno,
                     subscriberid, networktype, phonetype, phonelocation);


            //Display the data in console

            String deviceinfo = "";

            deviceinfo += ("Device ID: " + deviceid + "\n");
            deviceinfo += ("Phone Number: " + phonenumber + "\n");
            deviceinfo += ("Software Version: " + softwareversion + "\n");
            deviceinfo += ("Operator Name: " + operatorname + "\n");
            deviceinfo += ("SIM Country Code: " + simcountrycode + "\n");
            deviceinfo += ("SIM Operator: " + simoperator + "\n");
            deviceinfo += ("SIM Serial No.: " + simserialno + "\n");
            deviceinfo += ("Subscriber ID: " + subscriberid + "\n");
            deviceinfo += ("Network Type: " + networktype + "\n");
            deviceinfo += ("Phone Type: " + phonetype + "\n");
            deviceinfo += ("Phone Location: " + phonelocation + "\n");

            Log.d("Phone Info: ",deviceinfo);

            return phoneInfo;

        }


        private String getNetworkTypeString(int type){
            String typeString = "Unknown";

            switch(type)
            {
                case TelephonyManager.NETWORK_TYPE_EDGE:        typeString = "EDGE"; break;
                case TelephonyManager.NETWORK_TYPE_GPRS:        typeString = "GPRS"; break;
                case TelephonyManager.NETWORK_TYPE_UMTS:        typeString = "UMTS"; break;
                default:
                    typeString = "UNKNOWN"; break;
            }

            return typeString;
        }

        private String getPhoneTypeString(int type){
            String typeString = "Unknown";

            switch(type)
            {
                case TelephonyManager.PHONE_TYPE_GSM:   typeString = "GSM"; break;
                case TelephonyManager.PHONE_TYPE_NONE:  typeString = "UNKNOWN"; break;
                default:
                    typeString = "UNKNOWN"; break;
            }

            return typeString;
        }



        @Override
        public void saveDataLocally () {


            PhoneInfo phoneInfo = getPhoneInfo();


            Gson gson = new Gson();
            String phoneStatus = gson.toJson(phoneInfo);
            Log.d("Phone Status", phoneStatus);

            DataTable dataTable = new DataTable();
            dataTable.setPermission("phonestate");
            dataTable.setDataContent(phoneStatus);
            dbDataHandler dataPhone = new dbDataHandler(context);
            dataPhone.addData(dataTable);
            dataPhone.close();


        }


        public List<PhoneInfo> readDataFromLocalDb () {

            dbDataHandler dbData = new dbDataHandler(context);

            List<DataTable> data = dbData.getAllPhoneState();

            List<PhoneInfo> dataList = new ArrayList<>();

            for (DataTable dl : data) {

                // Get values from data row
                Gson gson = new Gson();
                String jsonString = dl.getDataContent();

                //convert the json string back to object
                PhoneInfo obj = gson.fromJson(jsonString, PhoneInfo.class);
                dataList.add(obj);

                System.out.println(obj);

            }
            return dataList;

        }



    @Override
    public boolean dataExist() {
        return true; // Data should be always available
    }
}


