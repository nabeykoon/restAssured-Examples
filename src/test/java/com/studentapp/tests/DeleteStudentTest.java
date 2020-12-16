package com.studentapp.tests;

import com.github.javafaker.Faker;
import com.studentapp.model.StudentPojo;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteStudentTest extends BaseTest {

    @Test
    public void deleteStudentTest() {

        given ()
                .when ()
                .contentType (ContentType.JSON)
                .when ()
                .delete ("/100")
                .then ()
                .statusCode (204);
    }
}
