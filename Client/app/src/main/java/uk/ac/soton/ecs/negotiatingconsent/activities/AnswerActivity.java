package uk.ac.soton.ecs.negotiatingconsent.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.R;
import uk.ac.soton.ecs.negotiatingconsent.collectables.DataClass;
import uk.ac.soton.ecs.negotiatingconsent.helpers.AppCoreData;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbHandler;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbQuestionHandler;
import uk.ac.soton.ecs.negotiatingconsent.models.Question;
import uk.ac.soton.ecs.negotiatingconsent.models.Status;
import uk.ac.soton.ecs.negotiatingconsent.services.AnswerService;


/**
 * User interface class concerned with the answering of questions
 */
public class AnswerActivity extends Activity {


    public String answeredQuestionId;
    public String answeredQuestionResponse;
    public String answeredQuestionText;
    public int answeredQuestionPoints;
    public String answeredQuestionPermission;
    public int newStatusID;
    Context context = AppCoreData.Context();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final dbQuestionHandler dbQuestion = new dbQuestionHandler(context);
        Gson gson = new Gson();
        final Question question  = dbQuestion.getFirstUnansweredQuestion();

        final String question_id = question.getQuestion_id();
        final String question_text = question.getQuestion_text();
        final String pointsText = question.getAvailable_points();
        final String questionPermission = question.getAssociate_action();
        final String request_type = question.getRequest_type();
        final List<String> questionResponses =  question.getQuestion_responses();

        // Set the view
        if (question_text.equals("review")){

            finish();
            Intent intent = new Intent(this, ReviewActivity.class);
            startActivity(intent);

        }
        setContentView(R.layout.activity_answer);


        final int points = Integer.parseInt(pointsText);

        // Find the number of responses the question has
       int n = questionResponses.size();

        // Get the question layout
        LinearLayout questionLayout = (LinearLayout) findViewById(R.id.questionPanel);

        // Get the button layout
        LinearLayout btnLayout = (LinearLayout) findViewById(R.id.buttonPanel);

        if(request_type.equals("practice"))
        {
            TextView practiceView = new TextView(this);
            practiceView.setText("Practice Question");
            practiceView.setTextSize(28);
            practiceView.setTextColor(Color.parseColor("#F21818"));
            questionLayout.addView(practiceView);
        }

        // Create the question text view
        TextView questionView = new TextView(this);
        questionView.setText(question_text);
        questionView.setTextSize(24);
        questionLayout.addView(questionView);
        setQuestionIcon(questionPermission,pointsText);

        // Add answer buttons to view
        for(int i = 0; i < n; i++) {
            final String option = questionResponses.get(i);
            Button nb = new Button(this);
            nb.setTextSize(20);
            nb.setText(option);
            int styleResource = R.drawable.gradient;
            nb.setBackgroundResource(styleResource);
            nb.setId(i);
            nb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendQuestionResponse(question_id, option, points, questionPermission,request_type);
                }
            });

            btnLayout.addView(nb);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer, menu);
        return true;
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
     * Function to collect a response to a question and begin the post request function
     * @param question_id
     * @param response
     * @param points
     * @param questionPermission
     * @param request_type
     */
    public void sendQuestionResponse(String question_id, String response, int points, final String questionPermission, String request_type)
    {
        dbQuestionHandler db = new dbQuestionHandler(context);
        // Remove data from DB

        db.deleteFirstUnansweredQuestion();

        if(!request_type.equals("practice"))
        {
            if(response.equals("Yes"))
            {
                collectAndSaveData(questionPermission);
            }

            // Create a new status object and save to the database
            Status status = new Status();

            status.setQuestion_id(question_id);
            status.setQuestion_response(response);
            status.setPending(1);
            status.setPoints(points);
            status.setStatusPermission(questionPermission);
            status.setSavedToServer(0);

            // Call function to save to local database
            saveToLocalDatabase(status);
            startAnswerService();

        }
        if(db.getAllQuestions().size()>0){
            finish();
            goToAnswerActivity();
        }
        else {
            finish();
            goToStatusActivity();
        }

    }

    /**
     * Function to send an intent to start the answer service
     */
    public void startAnswerService()
    {
        Intent serviceIntent = new Intent(this, AnswerService.class);
        startService(serviceIntent);

    }


    /**
     *Function to collect data and save to the local database
     * @param questionPermission
     */
    public void collectAndSaveData(String questionPermission)
    {
        final String answeredPermission = questionPermission;

        //Collect the data and save to the local database
        AnswerActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                DataClass dataClass = new DataClass(answeredPermission);
                dataClass.saveDataLocally();
                }
        });
    }


    /**
     * Function to save the status object to the database
     * @param status
     */
    public void saveToLocalDatabase(Status status)
    {
        dbHandler db = new dbHandler(this);
        db.addStatus(status);

    }

    /**
     * Function to send an intent to the status activity
     */
    public void goToStatusActivity()
    {
        Intent intent = new Intent(this,StatusActivity.class);
        startActivity(intent);
    }



    /**
     * Class which includes methods for an asynchronous task used to collect and save data from the user device
     */
    private class saveDataAsyncTask extends AsyncTask<Void,Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                //Collect the data and save to the local database
                AnswerActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        DataClass dataClass = new DataClass(answeredQuestionPermission);
                        dataClass.saveDataLocally();
                    }


                });

            }catch (Exception e)
            {
                Log.e("AnswerActivity", e.getMessage(), e);
            }

            return null;
        }

    }

    public void goToAnswerActivity(){
        Intent intent = new Intent(this,AnswerActivity.class);
        startActivity(intent);
    }

    public void setQuestionIcon(String permission, String points){

        if(permission.equals("browser history")){
            ImageView imageView = (ImageView) findViewById(R.id.shareImage);
            int resource = R.drawable.internet_earth;
            imageView.setImageResource(resource);
            TextView itemTxt = (TextView) findViewById(R.id.itemText);
            itemTxt.setText("Browser History");
        }
        else if(permission.equals("gallery")){
            ImageView imageView = (ImageView) findViewById(R.id.shareImage);
            int resource = R.drawable.gal;
            imageView.setImageResource(resource);
            TextView itemTxt = (TextView) findViewById(R.id.itemText);
            itemTxt.setText("Image Gallery");
        }
        else if(permission.equals("phonestate")){
            ImageView imageView = (ImageView) findViewById(R.id.shareImage);
            int resource = R.drawable.statephone;
            imageView.setImageResource(resource);
            TextView itemTxt = (TextView) findViewById(R.id.itemText);
            itemTxt.setText("Phone Status");
        }
        else if(permission.equals("contacts")){
            ImageView imageView = (ImageView) findViewById(R.id.shareImage);
            int resource = R.drawable.phonestate;
            imageView.setImageResource(resource);
            TextView itemTxt = (TextView) findViewById(R.id.itemText);
            itemTxt.setText("Contacts");
        }
        else if(permission.equals("location")){
            ImageView imageView = (ImageView) findViewById(R.id.shareImage);
            int resource = R.drawable.maps;
            imageView.setImageResource(resource);
            TextView itemTxt = (TextView) findViewById(R.id.itemText);
            itemTxt.setText("Location");
        }
        else if(permission.equals("calendar")){
            ImageView imageView = (ImageView) findViewById(R.id.shareImage);
            int resource = R.drawable.icons_calendar;
            imageView.setImageResource(resource);
            TextView itemTxt = (TextView) findViewById(R.id.itemText);
            itemTxt.setText("Calendar");
        }
        else if(permission.equals("sms")){
            ImageView imageView = (ImageView) findViewById(R.id.shareImage);
            int resource = R.drawable.emailicon;
            imageView.setImageResource(resource);
            TextView itemTxt = (TextView) findViewById(R.id.itemText);
            itemTxt.setText("Sms Messages");
        }
        TextView pointsTxt = (TextView) findViewById(R.id.pointsText);
        pointsTxt.setText(points);



    }



}
