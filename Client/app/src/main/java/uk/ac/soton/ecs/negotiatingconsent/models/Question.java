package uk.ac.soton.ecs.negotiatingconsent.models;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Dion Kitchener on 25/06/15.
 */
public class Question implements DatabaseErrorHandler {
    @JsonProperty
    private String question_id;
    @JsonProperty
    private String question_text;
    @JsonProperty
    private List<String> question_responses;
    @JsonProperty
    private String available_points;
    @JsonProperty
    private String associated_action;
    @JsonProperty
    private String request_type;

    public List<String> getQuestion_responses() { return this.question_responses; }
    public String getQuestion_id() {return this.question_id;}
    public String getQuestion_text() {
        return this.question_text;
    }
    public String getAssociate_action(){return this.associated_action;}
    public String getAvailable_points(){return this.available_points;}
    public String getRequest_type(){return this.request_type;}

    public void setAssociate_action(String associated_action){this.associated_action = associated_action;}
    public void setAvailable_points(String available_points){this.available_points = available_points;}
    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }
    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }
    public void setQuestion_responses(List<String> question_responses){ this.question_responses = question_responses; }
    public void setRequest_type(String request_type){this.request_type = request_type;}

    @Override
    public void onCorruption(SQLiteDatabase dbObj) {

    }
}
