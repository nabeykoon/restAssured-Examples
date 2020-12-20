package com.studentapp.loggingexamples;

import com.github.javafaker.Faker;
import com.studentapp.model.StudentPojo;
import com.studentapp.tests.BaseTest;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class LoggingResponseValues extends BaseTest {

    @Test
    public void printResponseHeaders() {
        System.out.println ("=======================Printing response headers=====================");
        given ()
                .param ("programme", "Computer Science")
                .param ("limit", 1)
                .when ()
                .get ("/list")
                .then ()
                .log ()
                .headers ()
                .statusCode (200);

    }

    @Test
    public void printResponseStatus() {
        System.out.println ("=======================Printing response status=====================");
        given ()
                .param ("programme", "Computer Science")
                .param ("limit", 1)
                .when ()
                .get ("/list")
                .then ()
                .log ()
                .status ()
                .statusCode (200);

    }

    @Test
    public void printResponseBody() {
        System.out.println ("=======================Printing response body=====================");
        given ()
                .param ("programme", "Computer Science")
                .param ("limit", 1)
                .when ()
                .get ("/list")
                .then ()
                .log ()
                .body ()
                .statusCode (200);

    }

    @Test
    public void printResponseBodyInCaseOfError() {
        System.out.println ("=======================Printing response body in case of error=====================");
        given ()
                .param ("programme", "Computer Science")
                .param ("limit", -1)
                .when ()
                .get ("/list")
                .then ()
                .log ()
                .ifError ()
                .statusCode (200);

    }

}
