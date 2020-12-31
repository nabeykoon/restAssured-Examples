package com.bestbuytrainingapp;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RootPathExamples {

    static ValidatableResponse validatableResponse;

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;

        validatableResponse = given ().when ().get ("/stores").then ();
    }


    @Test
    public void verifyUsingRootPath1() {
        validatableResponse
                .rootPath ("data[0].services[0]")
                .body ("name", equalTo ("Geek Squad Services"))
                .body ("createdAt", equalTo ("2016-11-17T17:56:35.881Z"))
                .rootPath ("data[0].services[0].storeservices")
                .body ("createdAt", equalTo ("2016-11-17T17:57:09.213Z"));
    }

    @Test
    public void verifyUsingRootPath2() {
        validatableResponse
                .rootPath ("data.services")
                .body ("name", hasItem (hasItem ("Geek Squad Services")))
        //above verify a collection containing a collection containing "Geek Squad Service"
                .body ("name", hasItem (hasItems ("Geek Squad Services", "Best Buy For Business")));

    }
}
