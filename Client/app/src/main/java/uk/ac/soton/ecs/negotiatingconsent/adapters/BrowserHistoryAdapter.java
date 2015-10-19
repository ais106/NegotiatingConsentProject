package uk.ac.soton.ecs.negotiatingconsent.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.R;
import uk.ac.soton.ecs.negotiatingconsent.collectables.Url;

/**
 * Created by Anna Soska on 05/09/15.
 */
public class BrowserHistoryAdapter extends ArrayAdapter<Url> {
    Context context;
    int layoutResourceId;
    List<Url> data = null;

    public BrowserHistoryAdapter(Context context, int layoutResourceId, List<Url> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UrlHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UrlHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.bicon);
            holder.textTitle = (TextView)row.findViewById(R.id.btitle);
            holder.textUrl = (TextView)row.findViewById(R.id.burl);
            holder.textVisits = (TextView)row.findViewById(R.id.bvisits);
            holder.textDate = (TextView)row.findViewById(R.id.bdate);


            row.setTag(holder);
        }
        else
        {
            holder = (UrlHolder)row.getTag();
        }

        Url url = data.get(position);


        holder.textTitle.setText(url.getTitle());
        holder.textUrl.setText(url.getUrl());
        byte[] bicon = url.getIcon();
        Bitmap icon;
        if(bicon!=null){
            //convert blob image data to Bitmap
               Bitmap  smallicon = BitmapFactory.decodeByteArray(bicon, 0, bicon.length);
            float scale;

            // Scale bitmap and keep aspect ratio
            int width  = smallicon.getWidth();
            int height = smallicon.getHeight();
            float scaleHeight = (float)height/(float)50;
            float scaleWidth  = (float)width /(float)50;
            if (scaleWidth < scaleHeight) {scale = scaleHeight;}
            else { scale = scaleWidth;}
            icon = Bitmap.createScaledBitmap(smallicon, (int)(width/scale), (int)(height/scale), true);

        }

        else{
            //default icon for history and bookmarks that do not icons
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.cast_ic_notification_0);
        }

        holder.imgIcon.setImageBitmap(icon);
        holder.textVisits.setText("Number of visits: "+url.getVisits());
        holder.textDate.setText("Last visit: "+url.getLastVisit());

        return row;
    }

    static class UrlHolder
    {
        public ImageView imgIcon;
        public TextView textTitle;
        public TextView textUrl;
        public TextView textVisits;
        public TextView textDate;

    }


}