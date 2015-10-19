package uk.ac.soton.ecs.negotiatingconsent.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import uk.ac.soton.ecs.negotiatingconsent.R;
import uk.ac.soton.ecs.negotiatingconsent.helpers.CheckInternetConnection;
import uk.ac.soton.ecs.negotiatingconsent.models.User;
import uk.ac.soton.ecs.negotiatingconsent.receivers.QuestionReceiver;


public class MainActivity extends Activity {

    public final static String SETTINGS_FILE ="mySettings";
    public final static String SETTINGS_ID = "user_id";
    public final static String SETTINGS_PASS = "pass_key";
    public final static String SETTINGS_USER_GROUP = "group_id";
    public final static String WEB_SERVICE_URL = "http://aicvm-tb1m13.ecs.soton.ac.uk/consent/register.php";
    public static final long NOTIFY_INTERVAL = 60 * 1000; // Notification frequency


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use a method to retrieve the stored user id value
        String user_id = getUserId();
        String pass_key = getUserPassKey();

        // Check if the user id value is empty, if so show the registration screen
        // else go directly to the answer activity screen
        if(user_id == null || pass_key == null) {
            setContentView(R.layout.activity_main);
        }
        else
        {
           userRegistered();
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }


    /**
     * Function to check share preferences for stored user id
     * @return user id number
     */
    public String getUserId()
    {
        SharedPreferences setting = getSharedPreferences(SETTINGS_FILE, 0);
        String user_id = setting.getString(SETTINGS_ID, null);
        return user_id;

    }

    /**
     *
     */
    public void userRegistered()
    {
        goToQuestionReceiver();
        finish();
        Intent intent = new  Intent(this, StatusActivity.class);
        startActivity(intent);
    }

    /**
     * Function to get the user pass key from the shared prefs store
     * @return user pass key
     */
    public String getUserPassKey()
    {
        SharedPreferences setting = getSharedPreferences(SETTINGS_FILE,0);
        String pass_key = setting.getString(SETTINGS_PASS, null);
        return pass_key;
    }

    /**
     * Function to save a user id in the share preferences key value store
     * @param user_id
     */
    public void saveUserId(String user_id)
    {
        SharedPreferences setting = getSharedPreferences(SETTINGS_FILE,0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(SETTINGS_ID, user_id);
        editor.commit();
    }

    /**
     * Function to save the user pass key in the shared preferences store
     * @param pass_key
     */
    public  void saveUserPassKey(String pass_key)
    {
        SharedPreferences setting = getSharedPreferences(SETTINGS_FILE,0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(SETTINGS_PASS, pass_key);
        editor.commit();
    }

    /**
     * Function to save the user group id
     * @param group_id
     */
    public void saveGroupId(String group_id)
    {
        SharedPreferences setting = getSharedPreferences(SETTINGS_FILE,0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(SETTINGS_USER_GROUP, group_id);
        editor.commit();

    }

    /**
     * Function to send an intent to the registration confirmation activity
     */
    public void goToConfirmationActivity()
    {
        finish();
        Intent intent = new Intent(this, RegisterConfirmActivity.class);
        startActivity(intent);
    }


    /**
     * Function to prepare an alarm service and pending intent to be sent at a specified time
     */
    public void goToQuestionReceiver()
    {
        Intent intent = new Intent(this, QuestionReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + NOTIFY_INTERVAL, pendingIntent);
    }

    /**
     * Function triggered when user clicks the button to begin the registration process
     */
    public void registerUser(View view)
    {

        if (new CheckInternetConnection().isInternetOn()) {
            // send get request to server for user id and pass key
            new RegisterRequestTask().execute();
        }
        else {
            internetNotAvailable();
        }
    }
    public void cancelMainActivity(View view)
    {

        finish();
    }


    /**
     * Function to provide a notification if the user device internet connection is not available
     */
    public void internetNotAvailable()
    {
        NotificationCompat.Builder mBuilder = new    NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_action_bell)
                .setContentTitle("Internet Unavailable!")
                .setContentText("Please turn on your internet connection");

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();


        mBuilder.setStyle(inboxStyle);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

    /**
     * Class which includes methods for an asynchronous task used to send a request to the web service
     */
    private class RegisterRequestTask extends AsyncTask<Void,Void,User>
    {

        @Override
        protected User doInBackground(Void... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            User user = restTemplate.getForObject(WEB_SERVICE_URL, User.class);
            return user;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(),e);
        }
            return null;
        }

        protected void onPostExecute(User user) {
            String newUserId = user.getId();
            String newPassKey = user.getPasskey();
            String newGroup_id = user.getGroup_id();
            saveUserPassKey(newPassKey);
            saveUserId(newUserId);
            saveGroupId(newGroup_id);
            goToConfirmationActivity();
        }
    }


}
