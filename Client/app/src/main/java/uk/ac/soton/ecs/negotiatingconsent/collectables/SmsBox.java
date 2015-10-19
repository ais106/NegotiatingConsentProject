package uk.ac.soton.ecs.negotiatingconsent.collectables;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.helpers.AppCoreData;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbDataHandler;

/**
 * Created by Anna Soska on 15/07/15.
 */
public class SmsBox implements Collectables {

    Context context = AppCoreData.Context();
    ContentResolver c = AppCoreData.ContentResolver();
    public Sms smsObj = null; // Create Sms object;
    final ArrayList<Sms> smsArray ;

    //Data that is collected
    String address = "";
    String person = "";
    String threadId = "";
    String date = "";
    String body = "";
    String type = "";

    public dbDataHandler dataSms = new dbDataHandler(context);
    public DataTable dataTable = new DataTable();

    public SmsBox() {
        smsArray = new ArrayList<>();

    }


    public ArrayList<Sms> getSmsBox (int sms_limit) {

        int number=0; // maximal number of urls to collect

        // Uri smsUri = Telephony.Sms.CONTENT_URI; // starts from API 19
        Uri smsUri = Uri.parse("content://sms");
        String[] reqCols = new String[]{Telephony.TextBasedSmsColumns.ADDRESS, Telephony.TextBasedSmsColumns.BODY, Telephony.TextBasedSmsColumns.THREAD_ID, Telephony.TextBasedSmsColumns.DATE, Telephony.TextBasedSmsColumns.TYPE};
        Cursor smsCur = c.query(smsUri, reqCols, null, null, null);
        smsCur.moveToFirst();


        if (smsCur.moveToFirst() && smsCur.getCount() > 0)

        {

            while (!smsCur.isAfterLast() && number < sms_limit) {

                address = smsCur.getString(smsCur.getColumnIndex(Telephony.TextBasedSmsColumns.ADDRESS)); //Number of the phone

                //person = smsCur.getString(smsCur.getColumnIndex(Telephony.TextBasedSmsColumns.PERSON)); //Contact name
                person = getContactName(context,address);

                body = smsCur.getString(smsCur.getColumnIndex(Telephony.TextBasedSmsColumns.BODY)); //Message content

                threadId = smsCur.getString(smsCur.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.THREAD_ID)); //Title of the website

                date = smsCur.getString(smsCur.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.DATE)); //Title of the website

                type = smsCur.getString(smsCur.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.TYPE)); //Title of the website

                smsObj = new Sms(address,person, body, threadId, date, type); // Creating a new object

                smsArray.add(smsObj); //Add data to the array

                smsCur.moveToNext();

                number++;
            }


            smsCur.close();

        }

        return smsArray;

    }

    public String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,
                new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

    @Override
    public void saveDataLocally() {

        List<Sms> smsList = getSmsBox(5);

        for(int i=0; i < smsList.size(); i++)

        {

            Gson gson = new Gson();
            String smsJson = gson.toJson(smsList.get(i));
            Log.d("Sms Save Data",smsJson);

            dataTable.setPermission("sms");
            dataTable.setDataContent(smsJson);
            dataSms.addData(dataTable);
            dataSms.close();


        }

    }

    public boolean dataExist() {
        Uri smsUri = Uri.parse("content://sms");
        String[] reqCols = new String[]{Telephony.TextBasedSmsColumns.ADDRESS,Telephony.TextBasedSmsColumns.PERSON, Telephony.TextBasedSmsColumns.BODY, Telephony.TextBasedSmsColumns.THREAD_ID, Telephony.TextBasedSmsColumns.DATE, Telephony.TextBasedSmsColumns.TYPE};
        Cursor smsCur = c.query(smsUri, reqCols, null, null, null);

        if (smsCur != null) {
            smsCur.moveToFirst();

            if (smsCur.moveToFirst() && smsCur.getCount() > 0)

            {
                smsCur.close();
                return true;
            }
            smsCur.close();
            return false;

        }

        return false;
    }

    public List<Sms> readDataFromLocalDb() {

        Context context = AppCoreData.Context();

        dbDataHandler dbData = new dbDataHandler(context);

        List<DataTable> dataSms = dbData.getAllSms();

        List<Sms> dataList = new ArrayList<>();

        for (DataTable dl : dataSms) {

            Gson gson = new Gson();
            String jsonString = dl.getDataContent();

            //convert the json string back to object
            Sms obj = gson.fromJson(jsonString, Sms.class);
            dataList.add(obj);

            System.out.println(obj);

        }
        return dataList;

    }


}
