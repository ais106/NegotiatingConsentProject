package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by Anna Soska on 11/09/15.
 */
public class UnansweredQuestion {

    private int id;
    private String question; //String Jason of Question object

    public UnansweredQuestion(){};

    public UnansweredQuestion(int id, String question){

        this.id = id;
        this.question = question;
    }


    public int getId(){
        return this.id;
    }

    public String getQuestion(){
        return this.question;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }



}
