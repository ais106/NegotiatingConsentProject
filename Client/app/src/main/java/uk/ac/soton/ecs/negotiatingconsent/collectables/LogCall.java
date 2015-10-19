package uk.ac.soton.ecs.negotiatingconsent.collectables;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.helpers.AppCoreData;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbDataHandler;

/**
 * Created by anna on 28/09/15.
 */
public class LogCall implements Collectables {

    Context context = AppCoreData.Context();
    ContentResolver cr = AppCoreData.ContentResolver();
    ArrayList<Calls> callsArray;

    public dbDataHandler dataContact = new dbDataHandler(context);
    public DataTable dataTable = new DataTable();


    public List<Calls> getCallDetails() {

        List<Calls> result = new ArrayList<>();
        Cursor managedCursor = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
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
        String nameStr="";

        while ( managedCursor.moveToNext() ) {
            nameStr =  managedCursor.getString( name );
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



        }
        managedCursor.close();
        Log.d("This is logcall: ", result.toString());
        return result;


    }

    @Override
    public void saveDataLocally() {

    }

    @Override
    public boolean dataExist() {
        return false;
    }
}
