package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by Anna Soska on 21/07/15.
 */
public class CalendarAccount
{
    private long CalendarId;
    private String CalendarName;
    private String CalendarAccountName;
    private String CalendarAccountType;


    public long getCalendarId(){
        return this.CalendarId;
    }

    public String getCalendarName(){return this.CalendarName;}

    public String getCalendarAccountType() {
        return CalendarAccountType;
    }

    public String getCalendarAccountName() {
        return CalendarAccountName;
    }


    public void setCalendarId(long calendarId){CalendarId = calendarId; }
    public void setCalendarName(String calendarName){CalendarName = calendarName;}
    public void setCalendarAccountName(String calendarAccountName){CalendarAccountName = calendarAccountName;}
    public void setCalendarAccountType(String calendarAccountType){CalendarAccountType = calendarAccountType;}



}


