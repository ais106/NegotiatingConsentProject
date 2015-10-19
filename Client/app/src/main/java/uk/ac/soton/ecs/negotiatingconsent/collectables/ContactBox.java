package uk.ac.soton.ecs.negotiatingconsent.collectables;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.helpers.AppCoreData;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbDataHandler;

/**
 * Created by Anna Soska on 21/07/15.
 */
public class ContactBox implements Collectables {

    Context context = AppCoreData.Context();
    ContentResolver cr = AppCoreData.ContentResolver();
    final ArrayList<Contact> contactArray;

    public dbDataHandler dataContact = new dbDataHandler(context);
    public DataTable dataTable = new DataTable();


    //Contact fields
    String name ="";
    String phone = null;
    String emailContact = null;
    String emailType = null;
    String phoneId=null;
    String emailId = null;
    String phoneType = null;
    String image_uri="";
    Bitmap bitmap = null;
    int counter=0;
    int limit = 5;
    String logcall="";


    public ContactBox(){
        contactArray = new ArrayList<>();

    }

    public ArrayList<Contact> getContactBox() {
        String id="";

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext() && counter < limit) {
                id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    System.out.println("name : " + name + ", ID : " + id);

                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneType = "Phone";

                        phoneId = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

                        switch(phoneId){
                            case "1" :
                                phoneType = "Home Phone";
                                break;
                            case "2" :
                                phoneType = "Mobile Phone";
                                break;
                            case "3" :
                                phoneType = "Work Phone";
                                break;
                        }


                        //System.out.println(phoneType+": " + phone);
                    }
                    pCur.close();
                    emailContact = "";
                    Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (emailCur.moveToNext()) {

                        emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        emailType =  "Email";
                        emailId = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        switch(emailId){
                            case "1" :
                                emailType = "Home Email";
                                break;
                            case "2" :
                                emailType = "Work Email";
                                break;

                        }

                        //System.out.println("Email " + emailContact + " Email Type : " + emailType);

                    }

                    emailCur.close();
                }

                if (image_uri != null) {
                    System.out.println(Uri.parse(image_uri));
                    try {
                        bitmap = MediaStore.Images.Media
                                .getBitmap(this.cr,
                                        Uri.parse(image_uri));

                        //System.out.println(bitmap);

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                //logcall = getCallDetails(phone);
                Contact contact = new Contact(name, phone, phoneType,emailContact, emailType, logcall, image_uri);
                contactArray.add(contact);
                counter = counter+1;

            }

        }
        cur.close();
        return contactArray;
    }

    // Get logcall
    public List<String> getCallDetails(String selectedNumber) {

        List<String> result = new  ArrayList<>();
        String selectionClause = CallLog.Calls.NUMBER + "=?";
        Cursor managedCursor = cr.query(CallLog.Calls.CONTENT_URI, null, selectionClause, new String[]{selectedNumber}, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
        int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
        String phNumber ="";
        String callType ="";
        String callDate ="";
        Date callDayTime;
        String callDuration ="";
        String dir=null;
        String logcall="";

        while ( managedCursor.moveToNext() ) {
            phNumber = managedCursor.getString( number );
            callType = managedCursor.getString(type);
            callDate = managedCursor.getString(date);
            callDayTime = new Date(Long.valueOf(callDate));
            callDuration = managedCursor.getString(duration);


            int dircode = Integer.parseInt( callType );
            switch( dircode ) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            logcall = "\n Call Type: " + dir + "\n Call Date:"+callDayTime+"\nCall duration in sec: \n"+callDuration;
            Log.d("This is logcall: ", logcall);
            result.add(logcall);




        }
        managedCursor.close();
        Log.d("This is logcall: ", result.toString());
        return result;


    }


    @Override
    public void saveDataLocally() {


        // Fetch the content of the collected object
        List<Contact> contactBoxList = getContactBox();

        for(int i=0; i < contactBoxList.size(); i++)

        {

            Gson gson = new Gson();
            String contactJson = gson.toJson(contactBoxList.get(i));
            dataTable.setPermission("contacts");
            dataTable.setDataContent(contactJson);
            dataContact.addData(dataTable);
            dataContact.close();


        }

    }


    @Override
    public boolean dataExist() {

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);

        if (cur.getCount() > 0) {

            return true;
        }
        else{
            return false;
        }
    }



    public List<Contact> readDataFromLocalDb() {

        Context context = AppCoreData.Context();

        dbDataHandler dbData = new dbDataHandler(context);

        List<DataTable> dataContact = dbData.getAllContacts();

        List<Contact> dataList = new ArrayList<>();

        for (DataTable dl : dataContact) {

            Gson gson = new Gson();
            String jsonString = dl.getDataContent();

            //convert the json string back to object
            Contact obj = gson.fromJson(jsonString, Contact.class);
            dataList.add(obj);

           // System.out.println(obj);

        }
        return dataList;

    }


}
