package uk.ac.soton.ecs.negotiatingconsent.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dion Kitchener on 29/06/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Answer {


    @JsonProperty
    private String question_id;

    @JsonProperty
    private String answer_id;

    public String getAnswer_id(){return  this.answer_id;}

    public String getQuestion_id() {
        return this.question_id;
    }

}
