package uk.ac.soton.ecs.negotiatingconsent.models;


/**
 * Created by Dion Kitchener on 02/07/15.
 */
public class Status {

    private int Id;
    private String StatusPermission;
    private int Points;
    private String Question_id;
    private String Question_response;
    private int Pending;
    private int SavedToServer;



    //Constructor
    public Status(int id, String permission, int points, String question_id, String question_response, int pending, int savedToServer) {
        this.Id = id;
        this.StatusPermission = permission;
        this.Points = points;
        this.Question_id = question_id;
        this.Question_response = question_response;
        this.Pending = pending;
        this.SavedToServer = savedToServer;
    }

    // Empty constructor
    public Status(){

    }


    // Constructor 2
    public Status(String permission, int points, String question_id, String question_response, int pending, int savedToServer) {
        this.StatusPermission = permission;
        this.Points = points;
        this.Question_id = question_id;
        this.Question_response = question_response;
        this.Pending = pending;
        this.SavedToServer = savedToServer;

    }

    public int getId() {
        return Id;
    }

    public int getPoints(){return Points;}

    public String getStatusPermission(){return StatusPermission;}

    public String getQuestion_id(){return Question_id;}
    public String getQuestion_response(){return Question_response; }
    public int getPending(){return  Pending;}
    public  int getSavedToServer(){return SavedToServer;}

    public void setId(int id){Id = id;}

    public void setPoints(int points){Points = points;}

    public void setStatusPermission(String statusPermission){StatusPermission = statusPermission;}

    public void setQuestion_id(String question_id){ Question_id = question_id;}

    public void setQuestion_response(String question_response){Question_response = question_response;}

    public void setPending(int pending){Pending = pending;}

    public void setSavedToServer(int savedToServer) {
        SavedToServer = savedToServer;
    }
}
