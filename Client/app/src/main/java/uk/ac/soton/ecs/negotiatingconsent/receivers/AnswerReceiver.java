package uk.ac.soton.ecs.negotiatingconsent.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import uk.ac.soton.ecs.negotiatingconsent.services.AnswerService;

/**
 * Created by Dion Kitchener on 02/09/15.
 */
public class AnswerReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, AnswerService.class);
        intent.putExtra("id","receiver");
        context.startService(serviceIntent);

    }
}
