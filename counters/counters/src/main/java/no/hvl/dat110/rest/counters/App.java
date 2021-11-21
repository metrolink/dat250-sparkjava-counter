package no.hvl.dat110.rest.counters;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;


import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App {
	
	static Counters counters = null;
	static ArrayList<Todo> todoList = null;

	static HashMap<Integer, Todo> todoMap = new HashMap<>();
	
	public static void main(String[] args) {

		
		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
			
		} else {
			port(8080);
		}

		todoMap.put(0, new Todo());

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
        	
        	//String newString = gson.toJson(todoList);

			String newString = gson.toJson(todoMap);
        	
        	return newString;
        });

		get("/todo/:id", (req,res) -> {

			Gson gson = new Gson();

			int index = Integer.parseInt(req.params("id"));

			/*Todo todos = todoList.get(index);

			return gson.toJson(todos);*/

			return todoMap.get(index).toJson();
		});


        
        put("/todo/:id", (req,res) -> {
            
        	Gson gson = new Gson();
        	
        	int index = Integer.parseInt(req.params("id"));
        	
        	/*Todo todos = todoList.get(index);
        	
        	Todo updated = gson.fromJson(req.body(), Todo.class);
        	
        	todos.setDescription(updated.getDescription());
        	
        	todos.setSummary(updated.getSummary());
        
            return gson.toJson(todos);*/

			todoMap.put(index, gson.fromJson(req.body(), Todo.class));

			return todoMap.get(index).toJson();
        	
        });
        
        post("/todo", (req,res) -> {
            
        	Gson gson = new Gson();
        	
        	/*Todo todos = gson.fromJson(req.body(), Todo.class);

			todoList.add(todos);
        
            return gson.toJson(todoList);*/

			int newId = todoMap.size();
			while (todoMap.containsKey(newId)) {
				newId += 1;
			}

			todoMap.put(newId, gson.fromJson(req.body(), Todo.class));

			return todoMap.get(newId).toJson();
        	
        });
        
        delete("/todo/:id", (req,res) -> {
            
        	Gson gson = new Gson();
        	
        	Integer n = Integer.parseInt(req.params("id"));
        	
        	/*todoList.remove(n);
        
            return gson.toJson(todoList);*/

			Todo deletedTodo = todoMap.get(n);
			todoMap.remove(n);

			return deletedTodo.toJson();
        	
        });
        
        
        
        
    }
    
}
