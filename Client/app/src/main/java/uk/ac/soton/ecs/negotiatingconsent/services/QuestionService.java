package uk.ac.soton.ecs.negotiatingconsent.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.R;
import uk.ac.soton.ecs.negotiatingconsent.activities.AnswerActivity;
import uk.ac.soton.ecs.negotiatingconsent.collectables.DataClass;
import uk.ac.soton.ecs.negotiatingconsent.collectables.DataTable;
import uk.ac.soton.ecs.negotiatingconsent.helpers.AppCoreData;
import uk.ac.soton.ecs.negotiatingconsent.helpers.CheckInternetConnection;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbDataHandler;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbQuestionHandler;
import uk.ac.soton.ecs.negotiatingconsent.models.Question;
import uk.ac.soton.ecs.negotiatingconsent.receivers.QuestionReceiver;


/**
 * Created by Anna Soska and Dion Kitchener on 15/07/15.
 */


public class QuestionService extends IntentService {


    public final static String SETTINGS_FILE ="mySettings";
    public final static String SETTINGS_ID = "user_id";
    public final static String SETTINGS_PASS = "pass_key";
    public final static String SETTINGS_USER_GROUP = "group_id";
    public final static String QUESTION_URL = "http://aicvm-tb1m13.ecs.soton.ac.uk/consent/getquestion.php";

    public long NOTIFY_INTERVAL = 60 * 1000; // Notification frequency
    private Intent thisIntent;
    Context context;
    dbQuestionHandler db = null;




    public  QuestionService()
    {
        super("QuestionService");
        context = AppCoreData.Context();

    }
    @Override
    public void onCreate()
    {
        super.onCreate();




    }

    @Override
   public  void onHandleIntent(Intent intent) {
        thisIntent = intent;

            if (new CheckInternetConnection().isInternetOn()) {
                newQuestionRequest();
            } else {
                internetNotAvailable();
            }

            callQuestionReceiver();

            stopService(intent);


    }


    public void newQuestionRequest()
    {
        new QuestionRequestTask().execute();
    }

    /**
     * Function to prepare a pending intent to be sent to the question receiver at a specified time
     */
    public void callQuestionReceiver()
    {
        Intent alarmIntent = new Intent(this, QuestionReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + NOTIFY_INTERVAL, pendingIntent);
    }


    /**
     * Function to provide a notification if the user device internet connection is not available
     */
    public void internetNotAvailable()
    {
        NotificationCompat.Builder mBuilder = new    NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_action_warning)
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
     * Function to prepare an intent to send question details to the answer activity
     * @param question_id
     * @param question_text
     * @param question_responses
     * @param associated_action
     * @param available_points
     * @param request_type
     */
    public void goToAnswerActivity(String question_id, String question_text, ArrayList<String> question_responses, String associated_action, String available_points, String request_type)
    {
        String question_type ="New Question";

        if(request_type.equals("practice"))
        {
            question_type = "Practice Question";
        }

        else {

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ontracticon)
                    .setContentTitle("You have new requests!")
                    .setContentText("Please tap here to continue")
                    .setTicker("Negotiating Consent - New Request !")
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVibrate(new long[0])
                    .setOngoing(true);

            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();

            int nId = 1;

            Intent intent = new Intent(this, AnswerActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(AnswerActivity.class);
            stackBuilder.addNextIntent(intent);

            PendingIntent answerPendingIntent = stackBuilder.getPendingIntent(nId, PendingIntent.FLAG_ONE_SHOT);
            mBuilder.setContentIntent(answerPendingIntent);
            mBuilder.setStyle(inboxStyle);
            mBuilder.setAutoCancel(true);
            mBuilder.setOnlyAlertOnce(false);

            Notification note = mBuilder.build();
            note.defaults |= Notification.DEFAULT_VIBRATE;
            note.defaults |= Notification.DEFAULT_SOUND;
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(nId, note);
        }
    }

    /**
     * Function to stop the question service if required
     */
    public void stopNotifications()
    {
        stopService(thisIntent);
    }

    /**
     * Class which includes methods for an asynchronous task used to send a request to the web service to request a question
     */
    private class QuestionRequestTask extends AsyncTask<Void, Void, Question>
    {
        @Override
        protected Question doInBackground(Void... params) {
            try {
                    // Get saved user details
                    SharedPreferences setting = getSharedPreferences(SETTINGS_FILE, 0);
                    String savedUserId = setting.getString(SETTINGS_ID, null);
                    String savedPass_key = setting.getString(SETTINGS_PASS, null);
                    String savedGroupId = setting.getString(SETTINGS_USER_GROUP, null);

                    // Create a key value map to store user details
                    MultiValueMap<String, String> userDetail = new LinkedMultiValueMap<String, String>();
                    userDetail.add("user_id", savedUserId);
                    userDetail.add("pass_key", savedPass_key);
                    userDetail.add("group_id", savedGroupId);


                    HttpHeaders requestHeaders = new HttpHeaders();

                    requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

                    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(userDetail, requestHeaders);

                    // Create a new rest template
                    RestTemplate rest = new RestTemplate(true);

                    // Get a question response from the web service
                    ResponseEntity<Question> response = rest.exchange(QUESTION_URL, HttpMethod.POST, requestEntity, Question.class);


                    Question question = response.getBody();
                    //saveUnansweredQuestionsLocally(question);
                    return question;


            } catch (Exception e) {
                Log.e("QuestionService", e.getMessage(), e);
            }
                return null;
            }


            protected void onPostExecute(Question question)
            {
                String question_id = question.getQuestion_id();
                String question_text = question.getQuestion_text();
                String request_type = question.getRequest_type();
                dbDataHandler data = new dbDataHandler(context);
                List<DataTable> dataTables = data.getAllData();


                if(question_text.equals("stop"))
                {
                    stopNotifications();
                }

                else if(question_text.equals("none"))
                {
                    callQuestionReceiver();

                }
                else
                {
                    String available_points = question.getAvailable_points();
                    String associated_action = question.getAssociate_action();
                    DataClass dataClass = new DataClass(associated_action);
                    boolean dataExist = dataClass.checkData();

                    if(dataExist || question_text.equals("review")){
                        ArrayList<String> question_responses;
                        question_responses = (ArrayList<String>) question.getQuestion_responses();
                        saveUnansweredQuestionsLocally(question); // Save question in a local db
                        goToAnswerActivity(question_id, question_text, question_responses, associated_action, available_points, request_type);

                    }

                }
            }
        }

    public void saveUnansweredQuestionsLocally(Question question){

        Log.d("Unanswered Question", String.valueOf(question));
        Context context = getApplicationContext();
        this.db = new dbQuestionHandler(context);

        db.addQuestion(question);
        db.close();


    }

    }
