package stepDefinitionpack;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.json.Json;

import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resource.APIResources;
import resource.TestDataBuild;
import resource.Utils;




public class stepDefinitionClass extends Utils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data =new TestDataBuild();


	@Given("Add place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String Address) throws IOException {

		res=given().spec(requestSpecification())
				.body(data.AddPlacePayLoad(name,language,Address));
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {

		//constructor will be called with value of resource which you pass
		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());


		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if(method.equalsIgnoreCase("POST"))
			response =res.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			response =res.when().get(resourceAPI.getResource());

	}

	@Then("API call got success with status code {int}")
	public void api_call_got_success_with_status_code(Integer int1) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(response.getStatusCode(),200);


	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {
		// Write code here that turns the phrase above into concrete actions

		String resp=response.asString();
		JsonPath js=new JsonPath(resp);
		assertEquals(js.get(keyValue),Expectedvalue);
	}









}








