package com.api.twitter.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.twitter.constants.EndPoints1;
import com.api.twitter.constants.Path1;
import com.api.twitter.utils.RestUtilities;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TweetStatusTests {

	RequestSpecification reqSpec;
	ResponseSpecification resSpec;

	@BeforeClass
	public void setup() {
		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.queryParam("user_id", "apiautomation");
		reqSpec.basePath(Path1.STATUSES);

		resSpec = RestUtilities.getResponseSpecification();
	}

	@Test
	public void readTweets1() {
		given().spec(RestUtilities.createQueryParam(reqSpec, "count", "1"))
				.when()
				.get(EndPoints1.STATUSES_USER_TIMELINE)
				.then()
				// .log().all()
				.spec(resSpec)
				.body("user.screen_name", hasItem("apiautomation"));
	}

	@Test
	public void readTweets2() {
		RestUtilities.setEndPoint(EndPoints1.STATUSES_USER_TIMELINE);
		Response res = RestUtilities.getResponse(RestUtilities.createQueryParam(reqSpec, "count", "2"), "get");
		ArrayList<String> screenNameList = res.path("user.screen_name");
		System.out.println("Read Tweets 2 Method");
		Assert.assertTrue(screenNameList.contains("apiautomation"));
	}
}