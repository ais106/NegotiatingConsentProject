package uk.ac.soton.ecs.negotiatingconsent.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.R;
import uk.ac.soton.ecs.negotiatingconsent.collectables.Contact;

/**
 * Created by Anna Soska on 04/09/15.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {

    Context context;
    int layoutResourceId;
    List<Contact> data = null;

    public ContactAdapter(Context context, int layoutResourceId, List<Contact> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ContactHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ContactHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.image);
            holder.textName = (TextView)row.findViewById(R.id.textName);
            holder.textPhone = (TextView)row.findViewById(R.id.textPhone);
            holder.textEmail = (TextView)row.findViewById(R.id.textEmail);

            row.setTag(holder);
        }
        else
        {
            holder = (ContactHolder)row.getTag();
        }

        Contact contact = data.get(position);
        if(contact.imageUri != null){
            holder.textName.setText(contact.name);
            holder.imgIcon.setImageURI(Uri.parse(contact.imageUri));
            holder.textPhone.setText(contact.getPhone());
            holder.textEmail.setText(contact.email);


        }
        else {

            holder.textName.setText(contact.name);
            holder.textPhone.setText(contact.getPhone());
            holder.textEmail.setText(contact.email);
            holder.textEmail.setText(contact.email);




        }


        return row;
    }

    static class ContactHolder
    {
        ImageView imgIcon;
        public TextView textName;
        public TextView textPhone;
        public TextView textEmail;

    }
}