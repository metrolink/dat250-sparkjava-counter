package no.hvl.dat110.rest.counters;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.gson.Gson;

//@Data
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //private int id;
    private String summary;
    private String description;

    public Todo(){

        this.summary = "Here is a summary";

        this.description = "Here is a description";
    }

    /*public Todo(int id, String sum, String desc){
        this.id = id;
        this.summary = sum;
        this.description = desc;

    }*/

    public Todo(String sum, String desc){
        this.summary = sum;
        this.description = desc;

    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Todo [summary=" + summary + ", description=" + description
                + "]";
    }
    
    String toJson () {
    	
    	Gson gson = new Gson();
    	    
    	String jsonInString = gson.toJson(this);
    	
    	return jsonInString;
    }

}
