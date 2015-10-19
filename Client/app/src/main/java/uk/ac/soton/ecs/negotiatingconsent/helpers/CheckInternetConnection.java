package uk.ac.soton.ecs.negotiatingconsent.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by anna on 28/07/15.
 */
public class CheckInternetConnection {

   Context context;

    public CheckInternetConnection() {
        context = AppCoreData.Context();
    }


    public final boolean isInternetOn() {


        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        return true;
                    }

        }

        return false;
    }


}
