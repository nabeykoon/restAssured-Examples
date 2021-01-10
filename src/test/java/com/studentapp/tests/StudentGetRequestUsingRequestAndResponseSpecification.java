package com.studentapp.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class StudentGetRequestUsingRequestAndResponseSpecification {

    static RequestSpecBuilder builder;
    static RequestSpecification requestSpec;

    static ResponseSpecBuilder responseSpecBuilder;
    static ResponseSpecification responseSpec;

    @BeforeClass
    public static void init(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/student";

        builder = new RequestSpecBuilder ();
        builder.addQueryParam ("programme", "Computer Science");
        builder.addQueryParam ("limit", 5);
        //builder.addHeader ();
        requestSpec = builder.build ();

        responseSpecBuilder = new ResponseSpecBuilder ();
        responseSpecBuilder.expectHeader ("Content-Type", "application/json;charset=UTF-8");
        responseSpecBuilder.expectStatusCode (200);
        responseSpecBuilder.expectBody ("[0].firstName", equalTo("Reece"));
        responseSpecBuilder.expectBody ("[1].firstName", equalTo("Orson"));
        responseSpecBuilder.expectResponseTime (lessThan (500L), TimeUnit.MILLISECONDS);
        responseSpec = responseSpecBuilder.build ();


    }

    @Test
    public void getCSStudents() {

        given ()
                .spec (requestSpec)
                .when ()
                .get ("/list")
                .then ()
                .spec (responseSpec)
                .body ("[4].firstName", equalTo("Peter"))
                .log ()
                .all ();

    }

    @Test
    public void extractResponseTimeForGetCSStudents() {

        long responseTime = given ()
                .spec (requestSpec)
                .when ()
                .get ("/list")
                .timeIn (TimeUnit.MILLISECONDS);
        System.out.println ("Response time is: " + responseTime + " " + TimeUnit.MILLISECONDS);

    }

    @Test
    public void VerifyResponseTimeForGetCSStudents() {

        given ()
                .spec (requestSpec)
                .when ()
                .get ("/list")
                .then()
                .spec (responseSpec)
                .time (lessThan (300L), TimeUnit.MILLISECONDS);

    }

}
