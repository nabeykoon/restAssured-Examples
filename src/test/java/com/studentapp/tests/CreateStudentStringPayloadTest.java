package com.studentapp.tests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateStudentStringPayloadTest extends BaseTest {

    @Test
    public void createNewStudentUsingStringPayload() {
        //Remove white space of json formatted String using http://jsonviewer.stack.hu/
        String payload = "{\"firstName\":\"Nad\",\"lastName\":\"Abeykoon\",\"email\":\"testing@gmail.com\",\"programme\":\"Computer science\",\"courses\":[\"C++\",\"JAVA\"]}";
        given ()
                .when ()
                .contentType (ContentType.JSON)
                .when ()
                .body (payload)
                .post ()
                .then ()
                .statusCode (201);
    }
}
