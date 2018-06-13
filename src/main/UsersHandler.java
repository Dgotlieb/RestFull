package main;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import main.DB_Connector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// test on :   http://localhost:8080/Rest_Full/rest/users   (/rest/ url pattern defined in web.xml)
@Path("/users")
public class UsersHandler {

  @Path("all/xml")
  @GET
  @Produces({ MediaType.APPLICATION_XML})
  public User[] getAllItemsAsXML() {
	 ArrayList<User> users = DB_Connector.readTable();
	 User[] items=new User[users.size()];
     return users.toArray(items);
  }
  
  @Path("all/json")
  @GET
  @Produces({MediaType.APPLICATION_JSON })
  public User[] getAllItemsAsJSON() {
	 ArrayList<User> users = DB_Connector.readTable();
	 User[] items=new User[users.size()];
     return users.toArray(items);
  }

  
  
  @Path("addUser/{name}/{password}/{email}")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Response addNewItem(@PathParam("token") String token, 
		  @PathParam("name") String name, 
		  @PathParam("password") String password, 
		  @PathParam("email") String email) {
	  
	  List<String> errors = new ArrayList<>();
	  boolean isValidUser = true;

	  if(!email.contains("@")||!email.contains(".")) {
		  return Response.status(400).entity("Invalid email address:\n"+email).build();
	  }

	  if(!isValidPassword(password,errors)) {
		  String tempError= "";
		  for (int i = 0; i < errors.size(); i++) {
			tempError +="\n"+errors.get(i);
		}
		  return Response.status(400).entity("Invalid password, please fix:\n"+tempError).build();

	  }
	  
	  List <User> list = DB_Connector.readTable();
	 
	  for (int i = 0; i < list.size(); i++) {
		User tempUser = list.get(i);
		if(tempUser.getEmail().equals(email)) {
			isValidUser = false;
		}
		if(tempUser.getName().equals(name)) {
			isValidUser = false;
		}
		
	}
	  if(isValidUser) {
	  DB_Connector.insertNewUser(name, password, email);
	  }else {
		  return Response.status(400).entity("User/Email already exist").build();  
	  }
	  
	  return Response.status(200).entity(name+" added").build();
  }
  
  
  public static boolean isValidPassword(String passwordhere, List<String> errorList) {

	    Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
	    Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
	    Pattern lowerCasePatten = Pattern.compile("[a-z ]");
	    Pattern digitCasePatten = Pattern.compile("[0-9 ]");
	    errorList.clear();

	    boolean flag=true;

	    if (passwordhere.length() < 8) {
	        errorList.add("Password lenght must have alleast 8 character !!");
	        flag=false;
	    }
	    if (!specailCharPatten.matcher(passwordhere).find()) {
	        errorList.add("Password must have atleast one specail character !!");
	        flag=false;
	    }
	    if (!UpperCasePatten.matcher(passwordhere).find()) {
	        errorList.add("Password must have atleast one uppercase character !!");
	        flag=false;
	    }
	    if (!lowerCasePatten.matcher(passwordhere).find()) {
	        errorList.add("Password must have atleast one lowercase character !!");
	        flag=false;
	    }
	    if (!digitCasePatten.matcher(passwordhere).find()) {
	        errorList.add("Password must have atleast one digit character !!");
	        flag=false;
	    }

	    return flag;

	}

} 
