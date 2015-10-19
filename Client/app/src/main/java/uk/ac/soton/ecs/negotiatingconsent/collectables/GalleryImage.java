package uk.ac.soton.ecs.negotiatingconsent.collectables;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.soton.ecs.negotiatingconsent.helpers.AppCoreData;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbDataHandler;

/**
 * Created by anna on 08/09/15.
 */
public class GalleryImage implements Collectables {



    Context context = AppCoreData.Context();
    ContentResolver cr = AppCoreData.ContentResolver();
    protected int counter = 0;
    private Bitmap myBitmap = null;
    GalleryObject galleryObject = null;
    List<GalleryObject> imageList;
    String encodedImageString;

    public GalleryImage() {
        imageList = new ArrayList<>();

    }


    List<GalleryObject> getGalleryImage() {

        String[] projection = new String[]{
               MediaStore.Images.Media.DATA,
        };

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        final Cursor cur = cr.query(images, projection, "", null, "");
        final int limit = 10;
        int counter=0;
        final ArrayList<String> imagesPath = new ArrayList<String>();
        if (cur.moveToFirst()) {

            int dataColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.DATA);
            do {
                imagesPath.add(cur.getString(dataColumn));
                counter=counter+1;
            } while (cur.moveToNext() && counter < 10);
        }
        cur.close();
        final Random random = new Random();
        final int count = imagesPath.size();

        int number = random.nextInt(count);
        String path = imagesPath.get(number);
        imagesPath.clear();
        File imgFile = new File(path);


        if (imgFile.exists()) {


            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Bitmap resized = ThumbnailUtils.extractThumbnail(myBitmap,500,700);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            encodedImageString = Base64.encodeToString(b, Base64.DEFAULT);
            GalleryObject object = new GalleryObject(encodedImageString);
            imageList.add(object);




        }
        return imageList;

    }

    @Override
    public void saveDataLocally() {

        DataTable dataTable = new DataTable();

        List<GalleryObject> list = getGalleryImage();

        dbDataHandler data = new dbDataHandler(context);

        for(int i=0; i < list.size(); i++)

        {


            Gson gson = new Gson();
            String json = gson.toJson(list.get(i));
            Log.d("Image Saved", json);
            dataTable.setPermission("gallery");
            dataTable.setDataContent(json);
            data.addData(dataTable);
            data.close();

        }


    }

    public List<GalleryObject> readDataFromLocalDb() {

        Context context = AppCoreData.Context();

        dbDataHandler dbData = new dbDataHandler(context);

        List<DataTable> data = dbData.getSpecificData("gallery");

        List<GalleryObject> dataList = new ArrayList<>();

        for (DataTable dl : data) {

            Gson gson = new Gson();
            String jsonString = dl.getDataContent();

            //convert the json string back to object
            GalleryObject obj = gson.fromJson(jsonString, GalleryObject.class);
            dataList.add(obj);

            System.out.println(obj);

        }
        return dataList;

    }


    @Override
    public boolean dataExist() {

        String[] projection = new String[]{
                MediaStore.Images.Media.DATA,
        };
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cur = cr.query(images, projection, "", null, "");
        if(cur.getCount() >0) {

            return true;
        }
        else {return  false;}
    }


}
