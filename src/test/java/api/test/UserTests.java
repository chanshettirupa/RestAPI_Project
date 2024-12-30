package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userpayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setUpData() {
		faker = new Faker();  // Prepare data 
		userpayload = new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testPostUser() {
		
		logger.info("**************Creating post User *******************");
		Response response = UserEndpoints.createUser(userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**************** User is created *******************");
		
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		logger.info("**************Get User Details *******************");
		Response response = UserEndpoints.readUser(this.userpayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**************User Details Displayed *******************");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		
		logger.info("**************Update User Details *******************");
		//update data using payload
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndpoints.updateUser(this.userpayload.getUsername(),userpayload);
		response.then().log().all();
		response.then().log().body().statusCode(200); //chai assertion
		Assert.assertEquals(response.getStatusCode(), 200); //TestNG assertion
		
		//checking data after update
		Response updatedresponse = UserEndpoints.readUser(this.userpayload.getUsername());
		updatedresponse.then().log().all();
		
		Assert.assertEquals(updatedresponse.getStatusCode(), 200);
		logger.info("**************Updated the User Details *******************");
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		logger.info("**************Delete User Details *******************");
		Response Deleteresponse = UserEndpoints.deleteUser(this.userpayload.getUsername());
		Deleteresponse.then().log().all();
		
		Assert.assertEquals(Deleteresponse.getStatusCode(), 200);
		logger.info("**************Deleted User Details *******************");
	}
	
	

}
