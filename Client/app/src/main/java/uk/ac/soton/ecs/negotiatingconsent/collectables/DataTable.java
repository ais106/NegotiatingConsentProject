package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by anna on 16/08/15.
 */
public class DataTable {

    private int id;
    private String permission;
    private int statusId;
    private String dataContent;


    //Constructor
    public DataTable(int id, String permission, String dataContent) {
        this.id = id;
        this.permission = permission;

        this.dataContent = dataContent;
    }

    // Empty constructor
    public DataTable(){

    }


    // Constructor 2
    public DataTable(String permission, String dataContent) {
        this.permission = permission;
        this.dataContent = dataContent;
    }

    public String getPermission() { return this.permission; }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDataContent() {
        return this.dataContent;
    }

    public void setDataContent(String points) {
        this.dataContent = points;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return this.id; }
}
