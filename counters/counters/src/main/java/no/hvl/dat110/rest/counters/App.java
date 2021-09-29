package no.hvl.dat110.rest.counters;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;


import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App {
	
	static Counters counters = null;
	static List<Todo> todoList = null;
	
	public static void main(String[] args) {

		
		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
			
		} else {
			port(8080);
		}

		counters = new Counters();
		todoList = new ArrayList<Todo>();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		get("/hello", (req, res) -> "Hello World!");
		
        get("/counters", (req, res) -> counters.toJson());
        
        get("/counters/red", (req, res) -> counters.getRed());

        get("/counters/green", (req, res) -> counters.getGreen());

        // TODO: put for green/red and in JSON
        // variant that returns link/references to red and green counter
        put("/counters", (req,res) -> {
        
        	Gson gson = new Gson();
        	
        	counters = gson.fromJson(req.body(), Counters.class);
        
            return counters.toJson();
        	
        });
        
        get("/todo", (req, res) -> { //could not translate ArrayList<Todo> into Todo.toJson();
        	Gson gson = new Gson();
        	
        	String newString = gson.toJson(todoList);
        	
        	return newString;
        });
        
        put("/todo/*", (req,res) -> {
            
        	Gson gson = new Gson();
        	
        	int index = Integer.parseInt(req.splat()[0])-1;
        	
        	Todo todos = todoList.get(index);
        	
        	Todo updated = gson.fromJson(req.body(), Todo.class);
        	
        	todos.setDescription(updated.getDescription());
        	
        	todos.setSummary(updated.getSummary());
        
            return gson.toJson(todos);
        	
        });
        
        post("/todo", (req,res) -> {
            
        	Gson gson = new Gson();
        	
        	Todo todos = gson.fromJson(req.body(), Todo.class);
        
            return todos.toJson();
        	
        });
        
        delete("/todo/*", (req,res) -> {
            
        	Gson gson = new Gson();
        	
        	String[] splat = req.splat();
        	
        	Integer n = Integer.parseInt(splat[0]);
        	
        	todoList.remove(n-1);
        
            return gson.toJson(todoList);
        	
        });
        
        
        
        
    }
    
}
