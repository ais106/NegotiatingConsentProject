package uk.ac.soton.ecs.negotiatingconsent.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.R;
import uk.ac.soton.ecs.negotiatingconsent.collectables.GetDate;
import uk.ac.soton.ecs.negotiatingconsent.collectables.Sms;

/**
 * Created by Anna Soska on 16/09/15.
 */
public class SmsAdapter2 extends ArrayAdapter<Sms> {
    Context context;
    int layoutResourceId;
    List<Sms> data = null;

    public SmsAdapter2(Context context, int layoutResourceId, List<Sms> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SmsHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new SmsHolder();
            holder.txtPerson = (TextView)row.findViewById(R.id.txtPerson);
            holder.txtNumber = (TextView)row.findViewById(R.id.txtNumber);
            holder.imageMsgType = (ImageView)row.findViewById(R.id.imageMsgType);
            holder.txtMsg = (TextView)row.findViewById(R.id.txtMsg);
            holder.txtDate = (TextView)row.findViewById(R.id.txtDate);


            row.setTag(holder);
        }
        else
        {
            holder = (SmsHolder)row.getTag();
        }

        Sms sms = data.get(position);
        String person = sms.getPerson();
        String address = sms.getAddress();
        String body = sms.getBody();
        String date = sms.getDate();
        String dayDate = new GetDate().getDateAndDay(date);
        String type = sms.getType();
        int imgType = getImgType(type);


        holder.txtPerson.setText(person);
        holder.txtNumber.setText(address);
        holder.imageMsgType.setImageResource(imgType);
        holder.txtMsg.setText(body);
        holder.txtDate.setText(dayDate);

        return row;
    }

    static class SmsHolder
    {

        public TextView txtPerson;
        public TextView txtNumber;
        public ImageView imageMsgType;
        public TextView txtMsg;
        public TextView txtDate;


    }

    int getImgType(String type){
        int result=0;
        switch (type){
            case "1": result = R.drawable.emailto;
                break;
            case "2": result =R.drawable.mailfrom;
                break;
            case "4": result = R.drawable.mailfrom;
                break;
            default: result = R.drawable.emailto;
                break;
        }

        return result;
    }

}
