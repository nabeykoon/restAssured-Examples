package com.studentapp.tests;

import com.github.javafaker.Faker;
import com.studentapp.model.StudentPojo;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class UpdateStudentPojoPayloadTest extends BaseTest {

    @Test
    public void updateStudentUsingPojoPayload() {
        StudentPojo student = new StudentPojo ();
        Faker faker = new Faker ();

        student.setEmail (faker.internet ().emailAddress ());

        given ()
                .when ()
                .contentType (ContentType.JSON)
                .when ()
                .body (student)
                .patch ("/101")
                .then ()
                .statusCode (200);
    }
}
