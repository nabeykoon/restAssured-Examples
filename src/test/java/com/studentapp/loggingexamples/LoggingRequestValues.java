package com.studentapp.loggingexamples;

import com.github.javafaker.Faker;
import com.studentapp.model.StudentPojo;
import com.studentapp.tests.BaseTest;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class LoggingRequestValues extends BaseTest {
    @Test
    public void printAllRequestHeaders() {
        System.out.println ("=======================Printing request headers=====================");
        given ()
                .log ()
                .headers ()
                .when ()
                .get ("/1")
                .then ()
                .statusCode (200);

    }

    @Test
    public void printRequestParameters() {
        System.out.println ("=======================Printing request parameters=====================");
        given ()
                .param ("programme", "Computer Science")
                .param ("limit", 1)
                .log ()
                .params ()
                .when ()
                .get ("/list")
                .then ()
                .statusCode (200);

    }

    @Test
    public void printRequestBody() {
        StudentPojo student = new StudentPojo ();
        Faker faker = new Faker ();
        List<String> courses = new ArrayList<String> ();
        courses.add ("JAVA");
        courses.add ("C++");

        student.setFirstName (faker.name ().firstName ());
        student.setLastName (faker.name ().lastName ());
        student.setEmail (faker.internet ().emailAddress ());
        student.setProgramme ("Computer science");
        student.setCourses (courses);

        System.out.println ("=======================Printing request Body=====================");
        given ()
                .log ()
                .body ()
                .when ()
                .contentType (ContentType.JSON)
                .when ()
                .body (student)
                .post ()
                .then ()
                .statusCode (201);
    }

    @Test
    public void printAll() {
        System.out.println ("=======================Printing All details=====================");
        given ()
                .param ("programme", "Computer Science")
                .param ("limit", 1)
                .log ()
                .all ()
                .when ()
                .get ("/list")
                .then ()
                .statusCode (200);

    }

    //print request details if test fails

    @Test
    public void printRequestDetails() {
        //System.out.println ("=======================Printing details due to failure=====================");
        given ()
                .param ("programme", "Computer Science")
                .param ("limit", 1)
                .log ()
                .ifValidationFails ()
                .when ()
                .get ("/list")
                .then ()
                .statusCode (201);

    }
}
