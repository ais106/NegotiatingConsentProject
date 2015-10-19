package uk.ac.soton.ecs.negotiatingconsent.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.R;
import uk.ac.soton.ecs.negotiatingconsent.collectables.GalleryObject;

/**
 * Created by Anna Soska on 09/09/15.
 */
public class GalleryAdapter extends ArrayAdapter<GalleryObject> {

    Context context;
    int layoutResourceId;
    List<GalleryObject> data = null;

    public GalleryAdapter(Context context, int layoutResourceId, List<GalleryObject> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GalleryHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new GalleryHolder();
            holder.imgGallery = (ImageView)row.findViewById(R.id.imageGallery);



            row.setTag(holder);
        }
        else
        {
            holder = (GalleryHolder)row.getTag();
        }

        GalleryObject galleryObject = data.get(position);
        String encodedImageString = galleryObject.getBmpString();

        byte[] bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
        Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0, bytarray.length);

        holder.imgGallery.setImageBitmap(bmimage);

        return row;
    }

    static class GalleryHolder
    {
        public ImageView imgGallery;


    }


}
