package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by Anna Soska on 04/09/15.
 */
public class UnknownDataClass implements Collectables {

    @Override
    public void saveDataLocally() {

    }

    @Override
    public boolean dataExist() {
        return false;
    }
}
