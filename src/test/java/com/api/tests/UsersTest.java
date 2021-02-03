package com.api.tests;

import org.testng.annotations.Test;

import com.api.entities.User;
import com.api.tests.base.BaseAPITest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class UsersTest extends BaseAPITest {

	@Test
	public void getUsers() {
		get(getRequest()).then().log().body().statusCode(200);
	}

	@Test
	public void createUser() throws JsonProcessingException {
		RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

		User userPojo = new User("Test", "Test@example.com", "Male", "Active");
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(userPojo);

		post(getRequest().body(jsonString)).then().log().body().statusCode(200);

	}

	@Test
	public void updateUser() throws JsonProcessingException {
		User userPojo = new User("User Not Found", "404@error.com", "Male", "Active");
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(userPojo);

		put(getRequest().body(jsonString), "")

				.then()
				.log()
				.body()
				.statusCode(404);

	}

	@Test
	public void deleteUser() {
		RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

		delete(getRequest(), "/1")

				.then()
				.statusCode(200);

	}
}
