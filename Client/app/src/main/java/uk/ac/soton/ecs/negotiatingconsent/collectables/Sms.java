package uk.ac.soton.ecs.negotiatingconsent.collectables;

/**
 * Created by anna on 20/07/15.
 */

    public class Sms{

        private String address ;
        private String person ;
        private String threadId ;
        private String date ;
        private String body ;
        private String type ;


        // Empty constructor
        public Sms(){

        }

        // constructor 2
        public Sms(String address,String person, String body, String threadId, String date,  String type){
            this.address = address;
            this.person = person;
            this.threadId = threadId;
            this.date = date;
            this.body = body;
            this.type = type;
    }


        public String getPerson() {
            return this.person;
        }

        public void setPerson(String person) { this.person = person; }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getThreadId() {
            return this.threadId;
        }

        public void setThreadId(String threadId) {
            this.threadId = threadId;
        }

        public String getDate() {
            return this.date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getBody() {
            return this.body;
        }

        public void setBody(String body) { this.body = body; }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

