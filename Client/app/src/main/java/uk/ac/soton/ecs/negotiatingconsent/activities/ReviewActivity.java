package uk.ac.soton.ecs.negotiatingconsent.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.R;
import uk.ac.soton.ecs.negotiatingconsent.adapters.BrowserHistoryAdapter;
import uk.ac.soton.ecs.negotiatingconsent.adapters.CalendarAdapter;
import uk.ac.soton.ecs.negotiatingconsent.adapters.ContactAdapter;
import uk.ac.soton.ecs.negotiatingconsent.adapters.GalleryAdapter;
import uk.ac.soton.ecs.negotiatingconsent.adapters.PhoneStateAdapter;
import uk.ac.soton.ecs.negotiatingconsent.adapters.SmsAdapter2;
import uk.ac.soton.ecs.negotiatingconsent.collectables.BrowserHistory;
import uk.ac.soton.ecs.negotiatingconsent.collectables.CalendarBox;
import uk.ac.soton.ecs.negotiatingconsent.collectables.CalendarEvent;
import uk.ac.soton.ecs.negotiatingconsent.collectables.Contact;
import uk.ac.soton.ecs.negotiatingconsent.collectables.ContactBox;
import uk.ac.soton.ecs.negotiatingconsent.collectables.DataTable;
import uk.ac.soton.ecs.negotiatingconsent.collectables.GalleryImage;
import uk.ac.soton.ecs.negotiatingconsent.collectables.GalleryObject;
import uk.ac.soton.ecs.negotiatingconsent.collectables.LocationData;
import uk.ac.soton.ecs.negotiatingconsent.collectables.PhoneInfo;
import uk.ac.soton.ecs.negotiatingconsent.collectables.PhoneStatus;
import uk.ac.soton.ecs.negotiatingconsent.collectables.Sms;
import uk.ac.soton.ecs.negotiatingconsent.collectables.SmsBox;
import uk.ac.soton.ecs.negotiatingconsent.collectables.TrackLocation;
import uk.ac.soton.ecs.negotiatingconsent.collectables.Url;
import uk.ac.soton.ecs.negotiatingconsent.helpers.AppCoreData;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbDataHandler;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbHandler;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbQuestionHandler;
import uk.ac.soton.ecs.negotiatingconsent.models.Status;
import uk.ac.soton.ecs.negotiatingconsent.services.AnswerService;


/**
 * Created by Anna Soska on 19/07/15.
 */
public class ReviewActivity extends FragmentActivity {


    // GUI Widget
    ListView listShowData;
    Button btn_submit;
    Button btn_cancel;
    TextView textTitle;
    GoogleMap mMap;
    public final String permission = getPermissionFromLocalDB();
    Context context = AppCoreData.Context();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!permission.equals("")) {

                displayLayout();


        }
        else {

            finish();
            gotoStatus();
        }


        }

    // Display an data-dependent layout
    public void displayLayout(){

        getLayoutId(permission);

        textTitle = (TextView) findViewById(R.id.textTitle);
        textTitle.setText("Share " + permission);

        //Submit the data
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Send the review response to local db
                sendReviewResponse(permission, "Share");
                updateStatus(permission);
                onClickAction();

            }

        });

        // Cancel the data submission
        btn_cancel = (Button) findViewById(R.id.btn_cancel);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                cancelPendingPoints(permission);
                startAnswerService();
                onClickAction();

            }
        });
    }



   public String getPermissionFromLocalDB(){

        Context context = AppCoreData.Context();
        dbDataHandler db = new dbDataHandler(context);
        List<DataTable> dataTables = db.getAllData();

        if (dataTables.size() > 0) {

            return dataTables.get(0).getPermission();
        }
        else {

            return "";
        }

    }

    public void updateStatus(String permission){
        if(permission!=null){
            Context context = AppCoreData.Context();
            dbHandler db = new dbHandler(context);
            db.updatePendingStatus(permission);

        }

    }

    public void cancelPendingPoints(String permission){
        if(permission!=null){
            Context context = AppCoreData.Context();
            dbHandler db = new dbHandler(context);
            db.cancelSharedData(permission);

        }


    }

    public void gotoStatus()
    {
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
    }

    private void onClickAction(){


        Context context = AppCoreData.Context();
        dbDataHandler db = new dbDataHandler(context);
        db.deleteRows(permission);

        if(checkIfDataExists()){
            finish();
            Intent intent = new Intent(this,ReviewActivity.class);
            startActivity(intent);

        }
        else {
            dbQuestionHandler dbQuestion2 = new dbQuestionHandler(context);
            dbQuestion2.deleteFirstUnansweredQuestion();
            finish();
            gotoStatus();
        }

    }

    public boolean checkIfDataExists(){


        Context context = AppCoreData.Context();
        dbDataHandler db = new dbDataHandler(context);
        List<DataTable> dataTables = db.getAllData();

        return dataTables.size() > 0;
    }



    public void getLayoutId(String permission){
        switch (permission) {
            case "location":
                setContentView(R.layout.activity_maps);
                setUpMapIfNeeded();

                break;
            case "contacts": {
                setContentView(R.layout.activity_reviewpanel);

                List<Contact> dataList = new ContactBox().readDataFromLocalDb();
                ContactAdapter dataAdapter = new ContactAdapter(this, R.layout.activity_contact_row2, dataList);

                // display the list.
                listShowData = (ListView) findViewById(R.id.listShowData);
                listShowData.setAdapter(dataAdapter);


                break;
            }
            case "browser history": {
                setContentView(R.layout.activity_reviewpanel);
                ImageView imageView = (ImageView) findViewById(R.id.reviewicon);
                int imageResource = R.drawable.internet_earth;
                imageView.setImageResource(imageResource);

                List<Url> list = new BrowserHistory().readDataFromLocalDb();
                BrowserHistoryAdapter dataAdapter = new BrowserHistoryAdapter(this, R.layout.activity_browser_row, list);
                listShowData = (ListView) findViewById(R.id.listShowData);
                listShowData.setAdapter(dataAdapter);


                break;
            }
            case "phonestate": {
                setContentView(R.layout.activity_reviewpanel);
                ImageView imageView = (ImageView) findViewById(R.id.reviewicon);
                int imageResource = R.drawable.statephone;
                // Drawable img = getResources().getDrawable(imageResource);
                imageView.setImageResource(imageResource);

                List<PhoneInfo> list = new PhoneStatus().readDataFromLocalDb();
                PhoneStateAdapter dataAdapter = new PhoneStateAdapter(this, R.layout.phonestatus, list);
                listShowData = (ListView) findViewById(R.id.listShowData);
                listShowData.setAdapter(dataAdapter);


                break;
            }
            case "gallery": {
                setContentView(R.layout.activity_reviewpanel);

                ImageView imageView = (ImageView) findViewById(R.id.reviewicon);
                int imageResource = R.drawable.gal;
                // Drawable img = getResources().getDrawable(imageResource);
                imageView.setImageResource(imageResource);

                List<GalleryObject> list = new GalleryImage().readDataFromLocalDb();
                GalleryAdapter dataAdapter = new GalleryAdapter(this, R.layout.activity_gallery_row, list);
                listShowData = (ListView) findViewById(R.id.listShowData);
                listShowData.setAdapter(dataAdapter);


                break;
            }
            case "calendar": {
                setContentView(R.layout.activity_reviewpanel);

                ImageView imageView = (ImageView) findViewById(R.id.reviewicon);
                int imageResource = R.drawable.icons_calendar;
                imageView.setImageResource(imageResource);

                List<CalendarEvent> list = new CalendarBox().readDataFromLocalDb();
                CalendarAdapter dataAdapter = new CalendarAdapter(this, R.layout.row_calendar, list);
                listShowData = (ListView) findViewById(R.id.listShowData);
                listShowData.setAdapter(dataAdapter);


                break;
            }
            case "sms": {
                setContentView(R.layout.activity_reviewpanel);

                ImageView imageView = (ImageView) findViewById(R.id.reviewicon);
                int imageResource = R.drawable.emailicon;

                imageView.setImageResource(imageResource);
                List<Sms> list = new SmsBox().readDataFromLocalDb();
                SmsAdapter2 dataAdapter = new SmsAdapter2(this, R.layout.row_sms2, list);
                listShowData = (ListView) findViewById(R.id.listShowData);
                listShowData.setAdapter(dataAdapter);


                break;
            }

        }

    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null ) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();

            }
        }
    }


    private void setUpMap() {

        List<LocationData> locationList = new TrackLocation().readDataFromLocalDb();

        for(int i=0; i < locationList.size(); i++)

        {

            String address = locationList.get(i).getAddress();
            double latitude = locationList.get(i).getLatitude();
            String city = locationList.get(i).getCity();
            double longitude = locationList.get(i).getLongitude();
            String latitudeStr = String.valueOf(latitude);
            String longitudeStr = String.valueOf(longitude);
            String timestamp = locationList.get(i).getDatestamp();

            String locationAll = "Address: " + address + "\n"
                    + "Longitude: " + longitudeStr +"\n"
                    + "Latitude: " + latitudeStr +"\n"+ timestamp;


            Log.d("Location Data", locationAll);
            LatLng newPosition = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(newPosition).title(city).snippet(address)).showInfoWindow();
            CameraUpdate newLocation = CameraUpdateFactory.newLatLngZoom(newPosition, 10);
            mMap.animateCamera(newLocation);


        }

    }

    public void sendReviewResponse( String permission, String buttonaction)
    {
            dbHandler db = new dbHandler(context);
            List<Status> statusList = db.getPendingStatus(permission);
            Status status = statusList.get(0);
            status.setQuestion_response(buttonaction);
            status.setSavedToServer(0);

            // Call function to save to local database
            saveToLocalDatabase(status);
            startAnswerService();
            db.close();


    }

    /**
     * Function to send an intent to start the answer service
     */
    public void startAnswerService()
    {
        Intent serviceIntent = new Intent(this, AnswerService.class);
        startService(serviceIntent);

    }

    public void saveToLocalDatabase(Status status)
    {
        dbHandler db = new dbHandler(this);
        db.addStatus(status);

    }


}