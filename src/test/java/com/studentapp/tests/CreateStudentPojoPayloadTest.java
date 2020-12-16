package com.studentapp.tests;

import com.github.javafaker.Faker;
import com.studentapp.model.StudentPojo;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateStudentPojoPayloadTest extends BaseTest {

    @Test
    public void createNewStudentUsingPojoPayload() {
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

        given ()
                .when ()
                .contentType (ContentType.JSON)
                .when ()
                .body (student)
                .post ()
                .then ()
                .statusCode (201);
    }
}
