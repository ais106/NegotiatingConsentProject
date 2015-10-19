package uk.ac.soton.ecs.negotiatingconsent.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.R;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbHandler;
import uk.ac.soton.ecs.negotiatingconsent.models.Status;


public class StatusActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get user status from database
        dbHandler db = new dbHandler(this);
        List<Status> statusList = db.getAllStatus();

        // Set up totals variables
        int totalPoint = 0;
        int totalPendingPoint = 0;


        // Set up views
        setContentView(R.layout.activity_status);
        TextView txtID = (TextView) findViewById(R.id.txtID);
        String userId = getUserId();
        txtID.setText("Your ID: "+ userId );
        TableLayout ptbl = (TableLayout) findViewById(R.id.pendingTableLayout);
        TableLayout tbl = (TableLayout) findViewById(R.id.dataSentTableLayout);

        // Set up table row parameters
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);


        for (Status sl:statusList)
        {

            // Get values from data row
            int pending = sl.getPending();
            String statusPermission = sl.getStatusPermission();
            int statusPoints = sl.getPoints();
            String answer = sl.getQuestion_response();

            if(answer.equals("Yes"))
            {


                String pointsString = String.valueOf(statusPoints);

                // Create new rows
                TableRow pendingTableRow = new TableRow(this);
                TableRow sentTableRow = new TableRow(this);

                // Set layout parameters for both rows
                pendingTableRow.setLayoutParams(rowParams);
                sentTableRow.setLayoutParams(rowParams);

                // Set up text views for displaying values
                TextView tPermission = new TextView(this);
                tPermission.setPadding(15, 2, 2, 2);

                TableRow.LayoutParams permParams = new TableRow.LayoutParams();
                permParams.column = 1;
                tPermission.setLayoutParams(permParams);

                TableRow.LayoutParams pointParams = new TableRow.LayoutParams();
                pointParams.column = 2;


                // Set total points text view
                TextView tPoints = new TextView(this);
                tPoints.setPadding(15, 2, 2, 2);
                tPoints.setTextSize(18);
                tPoints.setLayoutParams(pointParams);

                // Set text view to display data
                tPermission.setText(statusPermission);
                tPoints.setText(pointsString);
                tPermission.setTextSize(18);


                if (pending == 1) {
                    pendingTableRow.addView(tPermission);
                    pendingTableRow.addView(tPoints);


                    ptbl.addView(pendingTableRow);
                    totalPendingPoint = statusPoints + totalPendingPoint;
                } else {
                    sentTableRow.addView(tPermission);
                    sentTableRow.addView(tPoints);
                    tbl.addView(sentTableRow);
                    totalPoint = statusPoints + totalPoint;
                }

            }
        }

        String pointsText = String.valueOf(totalPoint);
        String pendingPointsText = String.valueOf(totalPendingPoint);
        

        // Add the status information to the existing text views
        TextView pointsView = (TextView) findViewById(R.id.pointsEarnedText);
        TextView pendingPointsView = (TextView) findViewById(R.id.pointsPendingText);

        pointsView.setText(pointsText);
        pendingPointsView.setText(pendingPointsText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void closeApp(View view){
        finish();
    }

    public String getUserId()
    {
        SharedPreferences setting = getSharedPreferences("mySettings",0);
        String pass_key = setting.getString("user_id", null);
        return pass_key;
    }

}
