package uk.ac.soton.ecs.negotiatingconsent.models;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dion Kitchener on 23/06/15.
 */


public class User {

    @JsonProperty
    private String user_id;
    @JsonProperty
    private String pass_key;
    @JsonProperty
    private String group_id;

    public  String getId() {
        return this.user_id;
    }

    public String getPasskey() {
        return this.pass_key;
    }

    public String getGroup_id() {return this.group_id;}

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPass_key(String pass_key) {
        this.pass_key = pass_key;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
