package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by Anna Soska and Dion Kitchener on 18/08/15.
 */
public class CalendarEvent
{

    private String calendarName;
    private long EventID;
    private String EventStart;
    private String EventEnd;
    private String EventTitle;
    private String eventDescription;
    private String eventLocation;

    public long getEventID() {
        return EventID;
    }

    public String getEventStart() {
        return EventStart;
    }

    public String getEventEnd() {
        return this.EventEnd;
    }

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventID(long eventID){EventID = eventID;}

    public void setEventStart(String eventStart) {
        EventStart = eventStart;
    }

    public void setEventEnd(String eventEnd) {
        EventEnd = eventEnd;
    }

    public void setEventTitle(String eventTitle) {
        EventTitle = eventTitle;
    }


    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDescription() {
       return this.eventDescription;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventLocation() {
        return this.eventLocation;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public String getCalendarName() {
        return this.calendarName;
    }

}

