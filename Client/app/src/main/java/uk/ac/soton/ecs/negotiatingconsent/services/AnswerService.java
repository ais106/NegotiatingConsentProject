package uk.ac.soton.ecs.negotiatingconsent.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.helpers.CheckInternetConnection;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbHandler;
import uk.ac.soton.ecs.negotiatingconsent.models.Answer;
import uk.ac.soton.ecs.negotiatingconsent.models.Status;
import uk.ac.soton.ecs.negotiatingconsent.receivers.AnswerReceiver;

/**
 * Created by Dion Kitchener and Anna Soska on 02/09/15.
 */

public class AnswerService extends IntentService {

    public final static String SETTINGS_FILE ="mySettings";
    public final static String SETTINGS_ID = "user_id";
    public final static String SETTINGS_PASS = "pass_key";
    public final static String ANSWER_URL = "http://aicvm-tb1m13.ecs.soton.ac.uk/consent/saveanswer.php";
    public final static long NOTIFY_INTERVAL = 10 * 60 * 1000;
    public List<Status> statusList;


    public AnswerService() {
        super("AnswerService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        statusList = getUnsavedAnswers();

        if (new CheckInternetConnection().isInternetOn())
        {
            newAnswerRequest();
        }
        callAnswerReceiver();
        stopService(intent);
    }


    private void callAnswerReceiver()
    {
        Intent alarmIntent = new Intent(this, AnswerReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + NOTIFY_INTERVAL, pendingIntent);

    }


    public List<Status> getUnsavedAnswers()
    {
        dbHandler db = new dbHandler(this);
        List<Status> unSavedStatuses = db.getUnsavedStatus();
        return unSavedStatuses;
    }


    public void newAnswerRequest()
    {
        new postUnsavedAnswersToServer().execute();
    }

    public void updateStatus(int statusId)
    {
        dbHandler db = new dbHandler(this);
        db.updateSavedStatus(statusId);
    }


    /**
     * Class which includes methods for an asynchronous task used to send a request to the web service
     */
    private class postUnsavedAnswersToServer extends AsyncTask<Void, Void, Answer> {


        @Override
        protected Answer doInBackground(Void... params) {


                // Get saved user details
                SharedPreferences setting = getSharedPreferences(SETTINGS_FILE,0);
                String savedUserId = setting.getString(SETTINGS_ID, null);
                String savedPass_key = setting.getString(SETTINGS_PASS, null);

                for (uk.ac.soton.ecs.negotiatingconsent.models.Status sl : statusList)
                {
                    if(!sl.equals(null)) {
                        String questionID = sl.getQuestion_id();
                        String question_response = sl.getQuestion_response();
                        int statusId = sl.getId();

                        // Create a key value map to store user details
                        MultiValueMap<String, String> answerDetail = new LinkedMultiValueMap<String, String>();
                        answerDetail.add("user_id", savedUserId);
                        answerDetail.add("pass_key", savedPass_key);
                        answerDetail.add("question_id", questionID);
                        answerDetail.add("answer_content", question_response);

                        HttpHeaders requestHeaders = new HttpHeaders();

                        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

                        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(answerDetail, requestHeaders);

                        // Create a new rest template
                        RestTemplate rest = new RestTemplate(true);

                        // Get a question response from the web service
                        ResponseEntity<Answer> response = rest.exchange(ANSWER_URL, HttpMethod.POST, requestEntity, Answer.class);


                        Answer serverResponse = response.getBody();

                        // if server response ok update the status
                        if (serverResponse != null) {
                            updateStatus(statusId);

                        }
                    }

                }
            return null;
        }

    }






}
