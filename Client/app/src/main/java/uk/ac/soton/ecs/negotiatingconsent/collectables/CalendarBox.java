package uk.ac.soton.ecs.negotiatingconsent.collectables;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.util.Log;

import com.google.gson.Gson;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.helpers.AppCoreData;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbDataHandler;

/**
 * Created by Anna Soska and Dion Kitchener on 21/07/15.
 */
public class CalendarBox implements Collectables
{

    Context context = AppCoreData.Context();
    private ContentResolver cr = AppCoreData.ContentResolver();
    final List<CalendarAccount> calendarList;
    final List<CalendarEvent> eventList;

    public dbDataHandler dataCalendar = new dbDataHandler(context);
    public DataTable dataTable = new DataTable();

    public CalendarBox() {
        calendarList = new ArrayList<>();
        eventList = new ArrayList<>();
    }


    public List<CalendarAccount> getCalendarList()
    {


        String[] projection =
                new String[]{
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.NAME,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.ACCOUNT_TYPE};
        Cursor calCursor =cr.
                        query(CalendarContract.Calendars.CONTENT_URI,
                                projection,
                                CalendarContract.Calendars.VISIBLE + " = 1",
                                null,
                                CalendarContract.Calendars._ID + " ASC");
        if (calCursor.moveToFirst()) {
            do {
                CalendarAccount cal = new CalendarAccount();

                long id = calCursor.getLong(0);
                String displayName = calCursor.getString(1);
                String accountName = calCursor.getString(2);
                String accountType = calCursor.getString(3);

                cal.setCalendarId(id);
                cal.setCalendarName(displayName);
                cal.setCalendarAccountName(accountName);
                cal.setCalendarAccountType(accountType);

                calendarList.add(cal);

            } while (calCursor.moveToNext());
        }
        return  calendarList;
    }

    /**
     *
     * @param calendarId
     * @return
     */
    public List<CalendarEvent> getCalendarEvents(long calendarId)
    {
        String[] proj =
            new String[]{
                    CalendarContract.Events._ID,
                    CalendarContract.Events.TITLE,
                    CalendarContract.Events.DTSTART,
                    CalendarContract.Events.DTEND,
                    CalendarContract.Events.DESCRIPTION,
                    CalendarContract.Events.EVENT_LOCATION};
        Cursor eventCursor = cr.
                        query(
                                CalendarContract.Events.CONTENT_URI,
                                proj,
                                CalendarContract.Events.CALENDAR_ID + " = ?",
                                new String[]{Long.toString(calendarId)},
                                CalendarContract.Events.DTSTART + " DESC");
        int limit = 0;
        if (eventCursor.moveToFirst()) {
                do {
                    CalendarEvent event = new CalendarEvent();
                    limit = limit+1;

                    long eventId = eventCursor.getColumnIndex(proj[0]);
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex(proj[1]));
                    String eventStart = eventCursor.getString(eventCursor.getColumnIndex(proj[2]));
                    String eventEnd = eventCursor.getString(eventCursor.getColumnIndex(proj[3]));
                    String eventDescription = eventCursor.getString(eventCursor.getColumnIndex(proj[4]));
                    String eventLocation = eventCursor.getString(eventCursor.getColumnIndex(proj[5]));

                    event.setEventID(eventId);
                    event.setEventStart(eventStart);
                    event.setEventEnd(eventEnd);
                    event.setEventTitle(eventTitle);
                    event.setEventDescription(eventDescription);
                    event.setEventLocation(eventLocation);

                    eventList.add(event);

                } while (eventCursor.moveToNext() && limit < 10);
            }
        return eventList;

        }

    @Override
    public  void saveDataLocally()
    {
        List<CalendarAccount> calList = getCalendarList();

        CalendarAccount cal = calList.get(0);

        long calId = cal.getCalendarId();
        String calTitle = cal.getCalendarName();
        List<CalendarEvent> calendarEvents = getCalendarEvents(calId);

        for(int i=0; i < calendarEvents.size(); i++)

        {


            Gson gson = new Gson();
            CalendarEvent obj = calendarEvents.get(i);
            obj.setCalendarName(calTitle);
            String calendarEventJson = gson.toJson(obj);
            Log.d("Calendar Save Data",calendarEventJson);

            dataTable.setPermission("calendar");
            dataTable.setDataContent(calendarEventJson);
            dataCalendar.addData(dataTable);
            dataCalendar.close();


        }


    }

    public List<CalendarEvent> readDataFromLocalDb() {

        Context context = AppCoreData.Context();

        dbDataHandler dbData = new dbDataHandler(context);

        List<DataTable> dataCalendar = dbData.getAllCalendar();

        List<CalendarEvent> dataList = new ArrayList<>();

        for (DataTable dl : dataCalendar) {

            Gson gson = new Gson();
            String jsonString = dl.getDataContent();

            //convert the json string back to object
            CalendarEvent obj = gson.fromJson(jsonString, CalendarEvent.class);
            dataList.add(obj);

            System.out.println(obj);

        }
        dbData.close();
        return dataList;

    }

    public static String getDateAndDay(String dateInMillisec ){
        String fullDate="";

        long longDate = Long.parseLong(dateInMillisec);
        Date dateEvent = new Date(longDate);
        String eventDate = new SimpleDateFormat("EEEE d MMM yyyy").format(dateEvent);

        fullDate = eventDate;

        return fullDate;

    }


    @Override
    public boolean dataExist() {
            Cursor cur = cr.
                    query(CalendarContract.Calendars.CONTENT_URI,
                            null,
                            CalendarContract.Calendars.VISIBLE + " = 1",
                            null,
                            CalendarContract.Calendars._ID + " ASC");

            if(cur.getCount()>0){
                cur.close();
                return true;
        }
        else {
                cur.close();
                return false;
        }


    }

}

