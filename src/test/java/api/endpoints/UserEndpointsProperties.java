package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

// created for perform create, read, update, delete request the user API.

public class UserEndpointsProperties {
	
	//method created for getting URL'd from properties file
	static ResourceBundle getURL(){
		ResourceBundle routes=ResourceBundle.getBundle("Routes");  //load the properties file
		return routes;
	}

	public static Response createUser(User payload) {
		
		String post_url = getURL().getString("post_url");    //take value from properties file
		
		Response response = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(payload)
			.when()
				.post(post_url);
		return response;
	}
	
	public static Response readUser(String username) {
		
		String get_url = getURL().getString("get_url");    //take value from properties file
		
		Response response = given()
				.pathParam("username", username)
			.when()
				.get(get_url);
		return response;
	}
	
	public static Response updateUser(String username, User payload) {
		
		String update_url = getURL().getString("update_url");    //take value from properties file
		
		Response response = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("username", username)
				.body(payload)
			.when()
				.put(update_url);
		return response;
	}
	
	public static Response deleteUser(String username) {
		
		String delete_url = getURL().getString("delete_url");    //take value from properties file
		
		Response response = given()
				.pathParam("username", username)
			.when()
				.delete(delete_url);
		return response;
	}
}
