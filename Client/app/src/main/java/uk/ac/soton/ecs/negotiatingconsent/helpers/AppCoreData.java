package uk.ac.soton.ecs.negotiatingconsent.helpers;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by Anna Soska on 15/07/15.
 */
public class AppCoreData extends Application {
    private static AppCoreData appData;
    private dbQuestionHandler questionHandler;

    public AppCoreData(){
        appData = this;

    }


    public static Context Context(){
        return appData;
    }

    public static ContentResolver ContentResolver(){

        return  appData.getContentResolver();
    }

    public static Context BaseContext(){

        return appData.getBaseContext();

    }
    public static dbQuestionHandler QuestionHandler(){

        return appData.questionHandler;

    }


}
