package uk.ac.soton.ecs.negotiatingconsent.collectables;

import android.content.ContentResolver;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.ac.soton.ecs.negotiatingconsent.helpers.AppCoreData;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbDataHandler;

/**
 * Created by Anna Soska on 16/08/15.
 */
public class TrackLocation implements  Collectables {


    public LocationManager locationManager;
    public String provider;
    public MyLocationListener mylistener;
    public Criteria criteria;
    public Context context = AppCoreData.Context();
    public ContentResolver cr = AppCoreData.ContentResolver();
    public dbDataHandler dbLocation = new dbDataHandler(context);
    public DataTable dataTable = new DataTable();
    public List<LocationData> locationList = new ArrayList<>();
    public LocationData loc = new LocationData();

    /**
     * Called when the activity is first created.
     */

    // Get the location manager
    @Override
    public void saveDataLocally() {



        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            mylistener = new MyLocationListener();

                // Define the criteria how to select the location provider
                criteria = new Criteria();

                criteria.setAccuracy(Criteria.ACCURACY_COARSE);    //default

                // user defines the criteria
                criteria.setCostAllowed(false);
                // get the best provider depending on the criteria
                provider = locationManager.getBestProvider(criteria, true);

                // the last known location of this provider
                Location location = locationManager.getLastKnownLocation(provider);

                if (location != null)

                {
                    mylistener.onLocationChanged(location);
                } else

                {
                    provider = locationManager.NETWORK_PROVIDER;

                }
                // location updates: at least 1 meter and 200millsecs change
                locationManager.requestLocationUpdates(provider, 0, 10, mylistener);

    }


    @Override
    public boolean dataExist() {
        return true;
    }


    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // Initialize the location fields
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            //get address
            String address = getCompleteAddressString(lat, lon);
            // Get city name
            String city = getCityName(lat,lon);
            // Get Current Date
            GetDate newDate = new GetDate();
            String timestamp = newDate.CurrentDate();
            LocationData locationData = new LocationData(lat, lon, city, address, timestamp);
            Gson gson = new Gson();
            String jsonString = gson.toJson(locationData);
            dataTable.setPermission("location");
            dataTable.setDataContent(jsonString);
            dbLocation.addData(dataTable);
            Log.d("Location", dataTable.getDataContent());
            dbLocation.close();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {


        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        public String getCityName(double latitude, double longitude){

            String cityName = null;
            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(latitude, longitude, 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
                return cityName;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cityName;

        }

        public String getCompleteAddressString(double latitude, double longitude) {
            String strAdd = "";
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");

                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                    strAdd = strReturnedAddress.toString();

                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            return strAdd;
        }
    }
    public List<LocationData> readDataFromLocalDb() {

        dbDataHandler dbData = new dbDataHandler(context);

        List<DataTable> data = dbData.getAllLocation();

        List<LocationData> dataList = new ArrayList<>();

        for (DataTable dl : data) {

            Gson gson = new Gson();
            String jsonString = dl.getDataContent();

            //convert the json string back to object
            LocationData obj = gson.fromJson(jsonString, LocationData.class);

            dataList.add(obj);

            System.out.println(obj);

        }
        dbData.close();
        return dataList;

    }

}


