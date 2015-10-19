package uk.ac.soton.ecs.negotiatingconsent.collectables;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anna on 22/08/15.
 */
public class GetDate {

    public String CurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd, HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public String getDateAndDay(String dateInMillisec ){
        String fullDate="";

        long longDate = Long.parseLong(dateInMillisec);
        java.sql.Date dateEvent = new java.sql.Date(longDate);
        String eventDate = new SimpleDateFormat("EEEE d MMM yyyy").format(dateEvent);

        fullDate = eventDate;

        return fullDate;

    }
}
