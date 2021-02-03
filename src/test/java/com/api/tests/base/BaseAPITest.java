package com.api.tests.base;

import static io.restassured.RestAssured.given;

import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseAPITest {

	@BeforeClass
	public void beforeClass(ITestContext context) {
		RestAssured.baseURI = "https://gorest.co.in/public-api/users";
	}

	public RequestSpecification getRequest() {
		RequestSpecification requestSpec = given().log()
				.uri()
				.log()
				.body()
				.log()
				.headers()
				.relaxedHTTPSValidation()
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.header("Content-Type", ContentType.JSON)
				.header("Authorization", "Bearer a2d62ecfe2b61dd4efa5e017f2ab587c0ff4a2a4c26c6a70d7696a7c56c637dc");
		return requestSpec;
	}

	enum RequestType {
		POST, //
		GET, //
		PUT, //
		DELETE;
	}

	public Response post(RequestSpecification request) {
		return sendRequest(RequestType.POST, request);
	}

	public Response get(RequestSpecification request) {
		return sendRequest(RequestType.GET, request);
	}

	public Response put(RequestSpecification request, String path) {
		return sendRequest(RequestType.PUT, request, path);
	}

	public Response delete(RequestSpecification request, String path) {
		return sendRequest(RequestType.DELETE, request, path);
	}

	private Response sendRequest(RequestType requestType, RequestSpecification request) {
		return sendRequest(requestType, request, null);
	}

	private Response sendRequest(RequestType requestType, RequestSpecification request, String path) {
		RequestSpecificationImpl requestSpecificationImpl = (RequestSpecificationImpl) request;
		Reporter.log("Request URL: " + requestSpecificationImpl.getURI(), true);
		Reporter.log("Request body: " + requestSpecificationImpl.getBody(), true);
		Reporter.log("Request headers: " + requestSpecificationImpl.getHeaders().toString(), true);
		Response response;
		switch (requestType) {
		case GET:
			response = request.when().get();
			break;
		case POST:
			response = request.when().post();
			break;
		case PUT:
			response = request.when().put(path);
			break;
		case DELETE:
			response = request.when().delete(path);
			break;

		default:
			throw new IllegalArgumentException("Invalid request type: " + requestType);

		}
		Reporter.log("Response status code: " + response.statusCode(), true);
		Reporter.log("Response body: " + response.body().asString(), true);
		return response;

	}

}
