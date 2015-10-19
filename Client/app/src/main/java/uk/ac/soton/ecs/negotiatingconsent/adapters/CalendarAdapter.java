package uk.ac.soton.ecs.negotiatingconsent.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.R;
import uk.ac.soton.ecs.negotiatingconsent.collectables.CalendarEvent;
import uk.ac.soton.ecs.negotiatingconsent.collectables.GetDate;

/**
 * Created by Anna Soska on 16/09/15.
 */
public class CalendarAdapter extends ArrayAdapter<CalendarEvent>{
    Context context;
    int layoutResourceId;
    List<CalendarEvent> data = null;

    public CalendarAdapter(Context context, int layoutResourceId, List<CalendarEvent> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CalendarHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new CalendarHolder();
            holder.textCalName = (TextView)row.findViewById(R.id.textCalName);
            holder.textCalStart = (TextView)row.findViewById(R.id.textCalStart);
            holder.textCalEnd = (TextView)row.findViewById(R.id.textCalEnd);


            row.setTag(holder);
        }
        else
        {
            holder = (CalendarHolder)row.getTag();
        }

        CalendarEvent calendarEvent = data.get(position);
        String dateStartStr = calendarEvent.getEventStart();
        String dateEndStr = calendarEvent.getEventEnd();
        String dateEnd ="";
        String  dateStart = "";

        if(dateStartStr!=null) {
          dateStart = new GetDate().getDateAndDay(dateStartStr);
        }
        if(dateEndStr!=null) {
            dateEnd = new GetDate().getDateAndDay(dateEndStr);
        }


        holder.textCalName.setText(calendarEvent.getEventTitle());
        holder.textCalStart.setText("Start Date: "+ dateStart);
        holder.textCalEnd.setText("End Date: " + dateEnd);

        return row;
    }

    static class CalendarHolder
    {

        public TextView textCalName;
        public TextView textCalStart;
        public TextView textCalEnd;


    }


}
