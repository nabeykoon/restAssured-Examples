package com.studentapp.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.FixMethodOrder;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class StudentGetRequestTests extends BaseTest {

    public void styles() {
        RestAssured.given ()
                .queryParams ("", "")
                .when ()
                .get ()
                .then ();

        RestAssured.given ()
                .expect ()
                .when ();

    }

    @Test(description = "Getting all the student from the database")
    public void getAllStudents() {
/*        RequestSpecification requestSpec = RestAssured.given ();
        Response response = requestSpec.get ("http://localhost:8080/student/list");
        response.prettyPrint ();
        ValidatableResponse validatableResponse = response.then ();
        validatableResponse.statusCode (200);*/

        RestAssured.given ()
                .when ()
                .get ("/list")
                .then ()
                .statusCode (200);

        RestAssured.given ()
                .expect ()
                .statusCode (200)
                .when ()
                .get ("/list");

//After static import of RestAssured class
        given ()
                .when ()
                .get ("/list")
                .then ()
                .statusCode (200);

    }

    @Test
    public void getSingleCSStudent() {

        Map<String, Object> params = new HashMap<String, Object> ();
        params.put ("programme", "Computer Science");
        params.put ("limit", 1);
        Response response = given ()
                //.queryParam ("programme", "Computer Science")
                //.queryParam ("limit", 1)
                //.queryParams ("programme", "Computer Science", "limit", 1)
                .queryParams (params)
                .when ()
                .get ("/list");

        response.prettyPrint ();
    }

    @Test
    public void getTheFirstStudent() {
        Response response =
                given ()
                        .pathParam ("id", 2)
                        .when ()
                        .get ("/{id}");
        response.prettyPrint ();
        //Reset static variables like BaseURI, port, etc
        //RestAssured.reset ();
    }
}
