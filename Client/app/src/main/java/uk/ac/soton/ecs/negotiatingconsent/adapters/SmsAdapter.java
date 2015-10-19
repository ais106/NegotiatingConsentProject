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
import uk.ac.soton.ecs.negotiatingconsent.collectables.Sms;

/**
 * Created by anna on 16/09/15.
 */
public class SmsAdapter extends ArrayAdapter<Sms> {
    Context context;
    int layoutResourceId;
    List<Sms> data = null;

    public SmsAdapter(Context context, int layoutResourceId, List<Sms> data) {
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
            holder.txtMsgType = (TextView)row.findViewById(R.id.txtMsgType);
            holder.txtMsg = (TextView)row.findViewById(R.id.txtMsg);


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
        String type = sms.getType();
        type = getMsgType(type);


        holder.txtPerson.setText(person);
        holder.txtNumber.setText(address);
        holder.txtMsgType.setText(type);
        holder.txtMsg.setText(body);

        return row;
    }

    static class SmsHolder
    {

        public TextView txtPerson;
        public TextView txtNumber;
        public TextView txtMsgType;
        public TextView txtMsg;


    }

    String getMsgType(String type){
        String result="";
        switch (type){
            case "1": result ="Inbox Message";
                break;
            case "2": result ="Sent Message";
                break;
            case "4": result = "Outbox Message";
                break;
            default: result = "Sms Message";
                break;
        }

        return result;
    }

}
