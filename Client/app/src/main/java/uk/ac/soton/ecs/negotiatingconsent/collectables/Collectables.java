package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by Anna Soska on 16/07/15.
 */

//Interface for data manipulation (get,save,display etc etc)
public interface Collectables {


    //List<String> showData(); //Collect data to display as string

    void saveDataLocally(); // save collected data locally

    boolean dataExist(); // Check if data exist




}
