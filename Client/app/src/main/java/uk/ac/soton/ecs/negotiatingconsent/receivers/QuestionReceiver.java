package uk.ac.soton.ecs.negotiatingconsent.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import uk.ac.soton.ecs.negotiatingconsent.services.QuestionService;

public class QuestionReceiver extends BroadcastReceiver {


    public QuestionReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent serviceIntent = new Intent(context, QuestionService.class);
        //serviceIntent.putExtra("id", "receiver");
        context.startService(serviceIntent);

    }


}
