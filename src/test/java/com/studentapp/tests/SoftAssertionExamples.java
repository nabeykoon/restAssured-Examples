package com.studentapp.tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SoftAssertionExamples extends BaseTest {

    @Test
    public void hardAssets(){
        given()
                .when ()
                .get ("/list")
                .then ()
                .body ("[0].firstName", equalTo("Vernon"))
                .body ("[0].lastName", equalTo("Harper"))
                .body ("[0].email", equalTo("egestas.rhoncus.Proin@massaQuisqueporttitor.org"))
                .body ("[0].programme", equalTo("Financial Analysis"));
    }

    @Test
    public void softAsserts(){
        given()
                .when ()
                .get ("/list")
                .then ()
                .body ("[0].firstName", equalTo("Vernon1"),
                        "[0].firstName", equalTo("Vernon"),
                        "[0].email", equalTo("egestas.rhoncus.Proin@massaQuisqueporttitor.org2"),
                        "[0].programme", equalTo("Financial Analysis") );
    }
}
