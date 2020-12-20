package com.bestbuytrainingapp;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class JsonPathJaywayExamples {

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
        Map<String, ?> rootElement = JsonPath.read (jsonResponse, "$");
        print (rootElement.toString ());
    }

    @Test
    public void getTotalValueFromResponse() {
        //Since Total is a Integer use Int to store value
        int totalValue = JsonPath.read (jsonResponse, "$.total");
        print (String.valueOf (totalValue));
    }
}
