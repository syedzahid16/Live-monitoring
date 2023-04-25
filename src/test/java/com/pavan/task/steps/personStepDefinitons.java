package com.pavan.task.steps;

import com.google.gson.*;
import com.pavan.task.helpers.RequestHelpers;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class personStepDefinitons {
    private String personName;

    private JsonObject personJson;
    private String responseBody;
    private int id;
    private String endpoint;
    private int personAge;
    private String personLocation;
    private HttpResponse<String> response;

    @Given("I have a person with the following details")
    public void i_have_a_person_with_the_following_details(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> person = rows.get(0);
        this.personName = person.get("name");
        this.personAge = Integer.parseInt(person.get("age"));
        this.personLocation = person.get("location");
    }
    @When("I create the person")
    public void i_create_the_person() {
        String requestBody = String.format("{\"name\": \"%s\", \"age\": %d, \"location\": \"%s\"}",
                this.personName, this.personAge, this.personLocation);
        this.response = RequestHelpers.sendPostRequestTo("/person", requestBody);
    }
    @Then("the response status code should be {string}")
    public void the_response_status_code_should_be(String expectedStatusCode) {
        int actualStatusCode = this.response.statusCode();
        assertEquals(Integer.parseInt(expectedStatusCode), actualStatusCode);
    }

    @Given("there is a person with ID {int}")
    public void there_is_a_person_with_id(Integer personId) {
        endpoint = "/person/" + personId;

    }
    @When("I request the person by ID")
    public void i_request_the_person_by_id() {
        response = RequestHelpers.sendGetRequestTo(endpoint);
        responseBody = response.body();
    }
    @Then("the response should contain the following details")
    public void theResponseShouldContainTheFollowingDetails(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> expectedData = dataTable.asMaps(String.class, String.class);

        List<Map<String, String>> actualData = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        Map<String, String> actualPerson = new HashMap<>();
        actualPerson.put("id", jsonObject.get("id").getAsString());
        actualPerson.put("name", jsonObject.get("name").getAsString());
        actualPerson.put("age", jsonObject.get("age").getAsString());
        actualPerson.put("location", jsonObject.get("location").getAsString());
        actualData.add(actualPerson);

        assertEquals(expectedData, actualData);
    }


    @Given("there are some persons in the system")
    public void there_are_some_persons_in_the_system() {
    }
    @When("I request all persons")
    public void i_request_all_persons() {
        response=RequestHelpers.sendGetRequestTo("/person");
    }
    @Then("the response should contain all persons")
    public void the_response_should_contain_all_persons() {
        String responseBody = response.body();
        Assert.assertTrue(responseBody.contains("Pavan"));
    }
//
//
//    @Given("there is a person with ID {int}")
//    public void there_is_a_person_with_id(Integer int1) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//    @Given("I have updated the person with the following details")
//    public void i_have_updated_the_person_with_the_following_details(io.cucumber.datatable.DataTable dataTable) {
//        // Write code here that turns the phrase above into concrete actions
//        // For automatic transformation, change DataTable to one of
//        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
//        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
//        // Double, Byte, Short, Long, BigInteger or BigDecimal.
//        //
//        // For other transformations you can register a DataTableType.
//        throw new io.cucumber.java.PendingException();
//    }
//    @When("I update the person by ID")
//    public void i_update_the_person_by_id() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//    @Then("the response status code should be {string}")
//    public void the_response_status_code_should_be(String string) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//    @Then("the response should contain the following details")
//    public void the_response_should_contain_the_following_details(io.cucumber.datatable.DataTable dataTable) {
//        // Write code here that turns the phrase above into concrete actions
//        // For automatic transformation, change DataTable to one of
//        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
//        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
//        // Double, Byte, Short, Long, BigInteger or BigDecimal.
//        //
//        // For other transformations you can register a DataTableType.
//        throw new io.cucumber.java.PendingException();
//    }

    @Given("there is a person with ID")
    public void there_is_a_person_with_id() {
        endpoint = "/person/" + "1";
    }
    @When("I delete the person by ID")
    public void i_delete_the_person_by_id() {
        response = RequestHelpers.sendDeleteRequestTo(endpoint);
    }
    @Then("the person should not exist with ID {int}")
    public void the_person_should_not_exist_with_id(Integer int1) {
        int statusCode = response.statusCode();
        Assertions.assertEquals(204, statusCode);
        response = RequestHelpers.sendGetRequestTo(endpoint);
        Assertions.assertEquals(404, response.statusCode());
    }
}
