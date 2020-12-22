package com.bestbuytrainingapp;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JsonPathJaywayExamples {

    //Check https://github.com/json-path/JsonPath for operators and their descriptions

    static String jsonResponse;

    private static void print(String val) {
        System.out.println ("----------------------------------------------------------");
        System.out.println (val);
        System.out.println ("----------------------------------------------------------");
    }

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;

        jsonResponse = given ().when ().get ("/products").asString ();
    }

    @BeforeMethod
    void startTestMethod() {
        System.out.println ("-----------------------Starting the test method-------------");
        System.out.println ("     ");
    }

    @AfterMethod
    void EndTestMethod() {
        System.out.println ("-----------------------Ending the test method-------------");
        System.out.println ("     ");
    }

    @Test
    public void getRoot() {
        //Root response returned with '{' means it should be stored in Hashmap
        Map<String, Object> rootElement = JsonPath.read (jsonResponse, "$");
        print (rootElement.toString ());
    }

    @Test
    public void getTotalValueFromResponse() {
        //Since Total is a Integer use Int to store value
        int totalValue = JsonPath.read (jsonResponse, "$.total");
        print (String.valueOf (totalValue));
    }

    @Test
    public void getAllDataElements(){
        //data is list of Hashmaps
        List<HashMap<String, Object>> dataElements = JsonPath.read (jsonResponse, "$.data");
        dataElements.forEach (System.out::println);
    }

    @Test
    public void getFirstDataElement() {
        Map<String, Object> firstDataElement = JsonPath.read (jsonResponse, "$.data[0]");
        print (firstDataElement.toString ());
    }

    @Test
    public void getLastDataElement() {
        Map<String, Object> lastDataElement = JsonPath.read (jsonResponse, "$.data[-1]");
        print (lastDataElement.toString ());
    }

    @Test
    public void getAllIdsUnderData(){
        List<String> listOfIds = JsonPath.read (jsonResponse, "$.data[*].id");
        print (listOfIds.toString ());
    }

    @Test
    public void getAllIdsFromResponse(){
        // .. means deep scan, within entire response
        List<String> allIds = JsonPath.read (jsonResponse, "$..[*].id");
        print (allIds.toString ());
    }

    @Test
    public void getNameOfProductsWhosePriceIsLessThan5(){
        List<String> names = JsonPath.read (jsonResponse, "$.data[?(@.price<7)].name");
        names.forEach (System.out::println);
    }
}
